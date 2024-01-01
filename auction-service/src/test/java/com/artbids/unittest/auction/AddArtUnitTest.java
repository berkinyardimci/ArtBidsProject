package com.artbids.unittest.auction;

import com.artbids.converter.AuctionConverter;
import com.artbids.data.request.AddArtRequestDto;
import com.artbids.data.response.AddArtResponse;
import com.artbids.entity.Auction;
import com.artbids.entity.AuctionItem;
import com.artbids.exception.AuctionNotFoundException;
import com.artbids.repository.AuctionItemRepository;
import com.artbids.repository.AuctionRepository;
import com.artbids.service.AuctionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddArtUnitTest {

    @InjectMocks
    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionItemRepository auctionItemRepository;

    @Test
    @DisplayName("Happy Path Test: add art to auction")
    void testAddArtGivenAuctionIdAndArtRequest_whenAddArt_thenAddArtResponse() {
        // given
        Long auctionId = 1L;
        AddArtRequestDto artRequestDto = AddArtRequestDto.builder()
                .productName("Fake Product")
                .productDescription("Fake Product Description")
                .startingPrice(100)
                .artistName("Fake Artist")
                .artistDescription("Fake Artist Description")
                .priceIncreaseRate(500)
                .build();
        Auction auction = new Auction();
        AuctionItem auctionItem = createAuctionItem(auctionId, artRequestDto);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionItemRepository.save(any(AuctionItem.class))).thenReturn(auctionItem);


        // when
        AddArtResponse response = auctionService.addArt(auctionId, artRequestDto);

        // then
        assertNotNull(response);
        assertEquals(artRequestDto.getProductName(), response.getProductName());
        assertEquals(artRequestDto.getProductDescription(), response.getProductDescription());

        verify(auctionItemRepository, times(1)).save(any(AuctionItem.class));
    }

    private AuctionItem createAuctionItem(Long auctionId, AddArtRequestDto artRequestDto) {
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setId(1L);
        auctionItem.setProductName(artRequestDto.getProductName());
        auctionItem.setProductDescription(artRequestDto.getProductDescription());
        auctionItem.setStartingPrice(artRequestDto.getStartingPrice());
        auctionItem.setArtistName(artRequestDto.getArtistName());
        auctionItem.setArtistDescription(artRequestDto.getArtistDescription());
        auctionItem.setPriceIncreaseRate(artRequestDto.getPriceIncreaseRate());

        Auction auction = new Auction();
        auction.setId(auctionId);
        auctionItem.setAuction(auction);

        return auctionItem;
    }

    @Test
    @DisplayName("Exception Test: auction not found")
    void testAddArtGivenInvalidAuctionId_whenAddArt_thenThrowAuctionNotFoundException() {
        // given
        Long invalidAuctionId = 2L;
        AddArtRequestDto artRequestDto = new AddArtRequestDto();

        when(auctionRepository.findById(invalidAuctionId)).thenReturn(Optional.empty());

        // when
        AuctionNotFoundException exception = assertThrows(AuctionNotFoundException.class, () -> {
            auctionService.addArt(invalidAuctionId, artRequestDto);
        });

        // then
        assertNotNull(exception);
        assertEquals("Müzayede bulunamadı", exception.getErrorMessage());
    }
}
