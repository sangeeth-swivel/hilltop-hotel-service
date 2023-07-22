package com.hilltop.hotel.controller;

import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import com.hilltop.hotel.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Hotel controller test
 * Unit tests for {@link  HotelController}
 */
class HotelControllerTest {

    private static final String FAILED = "Failed.";
    private final String ADD_HOTEL_URI = "/api/v1/hotel";
    private final String UPDATE_HOTEL_URI = "/api/v1/hotel";
    private final String LIST_ALL_HOTEL_URI = "/api/v1/hotel";
    private final String LIST_HOTEL_BY_LOCATION_AND_PAX_URI = "/api/v1/hotel?location=galle&paxCount=5";
    private final UpdateHotelRequestDto updateHotelRequestDto = getUpdateHotelRequestDto();
    @Mock
    private HotelService hotelService;
    private MockMvc mockMvc;
    @Mock
    private BaseController baseController;
    @InjectMocks
    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @BeforeEach
    void setUp() {
        openMocks(this);
        HotelController hotelController = new HotelController(hotelService);
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    /**
     * Unit tests for addHotel() method.
     */
    @Test
    void Should_ReturnOk_When_AddHotelIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ADD_HOTEL_URI)
                        .content(updateHotelRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_ADDED.getMessage()));
    }

    @Test
    void Should_ReturnBadRequest_When_MissingRequiredFields() throws Exception {
        HotelRequestDto requestDto = updateHotelRequestDto;
        requestDto.setHotelName(null);
        mockMvc.perform(MockMvcRequestBuilders.post(ADD_HOTEL_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    /**
     * Unit tests for updateHotel() method.
     */
    @Test
    void Should_ReturnOk_When_UpdateHotelIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_HOTEL_URI)
                        .content(updateHotelRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_UPDATED.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void Should_ReturnBadRequest_When_UpdateHotelFieldsAreMissing() throws Exception {
        HotelRequestDto requestDto = updateHotelRequestDto;
        requestDto.setHotelName(null);
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_HOTEL_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * Unit tests for searchHotelsByLocationAndPaxCount() method.
     */
    @Test
    void Should_ReturnOk_When_ListAllHotelsByLocationAndPaxIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LIST_HOTEL_BY_LOCATION_AND_PAX_URI)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_RETURNED.getMessage()));
    }

    @Test
    void Should_ReturnOk_When_ListAllHotelsIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LIST_ALL_HOTEL_URI)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_RETURNED.getMessage()));
    }

    /**
     * This method is used to mock hotelRequestDto.
     *
     * @return updateHotelRequestDto
     */
    private UpdateHotelRequestDto getUpdateHotelRequestDto() {
        UpdateHotelRequestDto updateHotelRequestDto = new UpdateHotelRequestDto();
        updateHotelRequestDto.setId("hid-123");
        updateHotelRequestDto.setHotelName("Hotel");
        updateHotelRequestDto.setHotelLocation("Colombo");
        return updateHotelRequestDto;
    }

    /**
     * Unit tests for GlobalControllerExceptionHandler
     */
    @Test
    void Should_ReturnInternalServerError_When_BookingIsFailedDueToInternalErrors(){
        HillTopHotelApplicationException exception = new HillTopHotelApplicationException("Failed.");
        ResponseEntity<ResponseWrapper> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Mock the behavior of the baseController
        when(baseController.getInternalServerError()).thenReturn(expectedResponse);

        // Invoke the exception handler
        ResponseEntity<ResponseWrapper> actualResponse = globalControllerExceptionHandler.hillTopUserApplicationException(exception);

        // Verify the interactions and assertions
        verify(baseController, times(1)).getInternalServerError();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

    /**
     *
     */
    @Test
    void testDataNotFoundException() {
        String errorMessage = "Data not found!";
        // Act and Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            throw new DataNotFoundException(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
}