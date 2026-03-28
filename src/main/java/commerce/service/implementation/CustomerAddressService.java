package commerce.service.implementation;

import commerce.dto.request.AddressRequest;
import commerce.dto.response.AddressResponse;
import commerce.entities.CustomerAddress;
import commerce.exceptions.ResourceNotFoundException;
import commerce.mappers.contracts.IAddressMapper;
import commerce.repositories.CustomerAddressRepository;
import commerce.service.contract.ICustomerAddressService;
import commerce.service.util.CustomerAddressHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerAddressService implements ICustomerAddressService {
    private final CustomerAddressHelper helper;
    private final CustomerAddressRepository addressRepository;
    private final IAddressMapper mapper;

    @Transactional
    @Override
    public AddressResponse createNewAddress(AddressRequest address) {
        CustomerAddress customerAddress = mapper.requestToEntity(address);
        customerAddress.setCustomer(helper.getCurrentCustomer());
        addressRepository.save(customerAddress);
        return null;
    }


    @Transactional
    @Override
    public AddressResponse updateAddressById(Integer addressId, AddressRequest address) {

        CustomerAddress customerAddress = helper.getAddressById(addressId);
        customerAddress.setCity(address.city());
        customerAddress.setStreet(address.street());
        customerAddress.setLabel(address.label());
        customerAddress.setNotes(address.notes());

        return mapper.toResponse(customerAddress);
    }

    @Override
    public void deleteAddressById(Integer addressId) {
        addressRepository.delete(helper.getAddressById(addressId));
    }

    @Override
    public List<AddressResponse> getAllAddresses() {
        return addressRepository.
                findAllByCustomerId(helper.getCurrentCustomer().getId())
                .stream().map(mapper::toResponse).toList();
    }

    public AddressResponse getAddressById(Integer addressId) {
        return addressRepository.findByIdAndCustomerId(helper.getCurrentCustomer().getId(), addressId).map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
    }
}
