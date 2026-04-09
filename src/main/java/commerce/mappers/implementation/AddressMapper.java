package commerce.mappers.implementation;

import commerce.dto.request.AddressRequest;
import commerce.dto.response.AddressResponse;
import commerce.entities.CustomerAddress;
import commerce.mappers.contracts.IAddressMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IAddressMapper {
    @Override
    public AddressResponse entityToResponse(CustomerAddress address) {
        return new AddressResponse(address.getAddressId(),address.getLabel(),
                address.getCity(),
                address.getStreet(),
                address.getNotes());
    }

    @Override
    public CustomerAddress requestToEntity(AddressRequest address) {
        return CustomerAddress.builder().label(address.label())
                .city(address.city())
                .street(address.street())
                .notes(address.notes()).build();
    }

    @Override
    public CustomerAddress responseToEntity(AddressResponse address) {
        return CustomerAddress.builder().label(address.label())
                .addressId(address.id())
                .city(address.city())
                .street(address.street())
                .notes(address.notes()).build();
    }
}
