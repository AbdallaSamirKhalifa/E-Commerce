package commerce.service.contract;

import commerce.dto.request.AddressRequest;
import commerce.dto.response.AddressResponse;

import java.util.List;

public interface ICustomerAddressService {
    AddressResponse createNewAddress(AddressRequest address);

    AddressResponse updateAddressById(Integer addressId, AddressRequest address);

    void deleteAddressById(Integer addressId);

    List<AddressResponse> getAllAddresses();

    AddressResponse getAddressById(Integer addressId);


}
