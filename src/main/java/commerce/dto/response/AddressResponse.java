package commerce.dto.response;


public record AddressResponse(
        Integer id,

        String label,

        String city,

        String street,

        String notes
) {

}
