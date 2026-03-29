package commerce.mappers.contracts;

import commerce.dto.request.AddressRequest;
import commerce.dto.response.AddressResponse;
import commerce.entities.CustomerAddress;

public interface IAddressMapper {
    AddressResponse toResponse(CustomerAddress address);

    CustomerAddress requestToEntity(AddressRequest address);

    CustomerAddress responseToEntity(AddressResponse address);
}
