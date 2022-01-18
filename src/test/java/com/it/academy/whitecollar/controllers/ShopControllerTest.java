package com.it.academy.whitecollar.controllers;

import com.it.academy.whitecollar.builders.PictureBuilder;
import com.it.academy.whitecollar.builders.ShopBuilder;
import com.it.academy.whitecollar.dtos.PictureDTO;
import com.it.academy.whitecollar.dtos.ShopDTO;
import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import com.it.academy.whitecollar.services.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(ShopController.class)
public class ShopControllerTest {

    @MockBean
    private ShopServiceImpl shopServiceImpl;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<List<ShopDTO>> jsonShopDTOList;
    @Autowired
    private JacksonTester<ShopDTO> jsonShopDTO;
    @Autowired
    private JacksonTester<List<PictureDTO>> jsonPictureDTOList;
    @Autowired
    private JacksonTester<PictureDTO> jsonPictureDTO;
    private ShopBuilder shopBuilder;
    private PictureBuilder pictureBuilder;

    @BeforeEach
    public void setUp(){
        this.shopBuilder = new ShopBuilder();
        this.pictureBuilder = new PictureBuilder();
    }

    @Test
    public void testGivenCorrectShopDTOWhenCreateShopThenReturnCreated() throws Exception {
        Shop shop = this.shopBuilder.validShop().build();
        ShopDTO shopDTO = new ShopDTO(shop);
        given(this.shopServiceImpl.createShop(shopDTO)).willReturn(shopDTO);

        MockHttpServletResponse response = mvc.perform(
                post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonShopDTO.write(shopDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    public void testGivenNotCorrectShopDTOWhenCreateShopThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("BAD REQUEST")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testGivenSavedShopsWhenGetAllShopsThenReturnOK() throws Exception {
        Shop shop = this.shopBuilder.validShop().build();
        List<ShopDTO> shopsDTO = List.of(new ShopDTO(shop));
        given(this.shopServiceImpl.getAllShops()).willReturn(shopsDTO);

        MockHttpServletResponse response = mvc.perform(
                get("/shops")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(jsonShopDTOList.write(shopsDTO).getJson()));
    }

    @Test
    public void testGivenNonSavedShopsWhenGetAllShopsThenReturnNoContent() throws Exception {
        List<ShopDTO> shopsDTO = List.of();
        given(this.shopServiceImpl.getAllShops()).willReturn(shopsDTO);

        MockHttpServletResponse response = mvc.perform(
                get("/shops")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void testGivenCorrectPictureDTOAndPresentShopIdWhenCreatePictureThenReturnCreated() throws Exception {
        Long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        PictureDTO pictureDTO = new PictureDTO(picture);
        given(this.shopServiceImpl.createPicture(presentId,pictureDTO)).willReturn(pictureDTO);

        MockHttpServletResponse response = mvc.perform(
                post("/shops/1/pictures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPictureDTO.write(pictureDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    public void testGivenCorrectPictureDTOAndNotPresentShopIdWhenCreatePictureThenReturnNotFound() throws Exception {
        Long notPresentId = 1L;
        Shop shop = this.shopBuilder.invalidShop().build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        PictureDTO pictureDTO = new PictureDTO(picture);
        given(this.shopServiceImpl.createPicture(notPresentId,pictureDTO)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                post("/shops/1/pictures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPictureDTO.write(pictureDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testGivenIncorrectPictureDTOWhenCreatePictureThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/shops/1/pictures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("BAD REQUEST")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testGivenSavedPicturesAndPresentShopIdWhenGetAllPicturesByIdShopThenReturnOK() throws Exception {
        Long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        List<PictureDTO> picturesDTO = List.of(new PictureDTO(picture));
        given(this.shopServiceImpl.getAllPictures(presentId)).willReturn(picturesDTO);

        MockHttpServletResponse response = mvc.perform(
                get("/shops/1/pictures")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(jsonPictureDTOList.write(picturesDTO).getJson()));
    }

    @Test
    public void testGivenNonSavedPicturesAndPresentShopIdWhenGetAllPicturesByIdShopThenReturnNoContent() throws Exception {
        Long presentId = 1L;
        given(this.shopServiceImpl.getAllPictures(presentId)).willReturn(List.of());

        MockHttpServletResponse response = mvc.perform(
                get("/shops/1/pictures")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void testGivenNotPresentShopIdWhenGetAllPicturesByIdShopThenReturnNotFound() throws Exception {
        Long notPresentId = 1L;
        given(this.shopServiceImpl.getAllPictures(notPresentId)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get("/shops/1/pictures")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testGivenPresentShopIdWhenDeleteAllPicturesThenReturnNoContent() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                delete("/shops/1/pictures")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void testGivenIncorrectShopIdWhenDeleteAllPicturesThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                delete("/shops/BAD_ID/pictures")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

}
