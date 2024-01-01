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
        AddArtRequestDto artRequestDto = createAddArtRequestDto();
        Auction auction = new Auction();
        AuctionItem auctionItem = createAuctionItem();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionItemRepository.save(any(AuctionItem.class))).thenReturn(auctionItem);

        // when
        AddArtResponse response = auctionService.addArt(auctionId, artRequestDto);

        // then
        assertNotNull(response);
        assertEquals(artRequestDto.getProductName(), response.getProductName());
        assertEquals(artRequestDto.getProductDescription(), response.getProductDescription());
        assertEquals(artRequestDto.getStartingPrice(), response.getStartingPrice());
        assertEquals(artRequestDto.getArtistName(), response.getArtistName());
        assertEquals(artRequestDto.getArtistDescription(), response.getArtistDescription());
        assertEquals(artRequestDto.getPriceIncreaseRate(), response.getPriceIncreaseRate());
        assertArrayEquals(artRequestDto.getImageData(), response.getImageData());

        verify(auctionItemRepository, times(1)).save(any(AuctionItem.class));
    }

    private AddArtRequestDto createAddArtRequestDto() {
        AddArtRequestDto artRequestDto = new AddArtRequestDto();
        artRequestDto.setProductName("Fake Product");
        artRequestDto.setProductDescription("Fake Product Description");
        artRequestDto.setStartingPrice(100);
        artRequestDto.setArtistName("Fake Artist");
        artRequestDto.setArtistDescription("Fake Artist Description");
        artRequestDto.setPriceIncreaseRate(5);
        artRequestDto.setImageData(new byte[]{1, 2, 3});

        return artRequestDto;
    }

    private AuctionItem createAuctionItem() {
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setId(1L);
        auctionItem.setProductName("Fake Product");
        auctionItem.setProductDescription("Fake Product Description");
        auctionItem.setStartingPrice(100);
        auctionItem.setArtistName("Fake Artist");
        auctionItem.setArtistDescription("Fake Artist Description");
        auctionItem.setPriceIncreaseRate(5);
        auctionItem.setImageData(new byte[]{1, 2, 3});

        Auction auction = new Auction();
        auction.setId(1L);
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
