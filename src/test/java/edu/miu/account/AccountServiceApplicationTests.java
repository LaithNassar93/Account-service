package edu.miu.account;

import edu.miu.account.entity.Account;
import edu.miu.account.entity.Address;
import edu.miu.account.entity.Gender;
import edu.miu.account.model.AuthenticateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.nio.charset.Charset;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAddUser() throws Exception {
		Account request = new Account(null,"Laith","Nassar","laith@gmail.com","123123",null,null,Gender.Male,new Address("IA","F","57552"),new Date());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(request );
		mockMvc.perform(post("/api/v1/account/").contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))).content(requestJson)).andExpect(status().isOk());
	}

	@Test
	public void testAuthenticate() throws Exception {
		AuthenticateRequest request = new AuthenticateRequest("laith@gmail.com","123123");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(request );
		mockMvc.perform(post("/api/v1/account/authenticate").contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))).content(requestJson)).andExpect(status().isOk());
	}

}
