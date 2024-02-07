package com.tunehub.project.controller;


import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.project.entities.Users;
import com.tunehub.project.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

//@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	UsersService service;

	@GetMapping("/pay")
	public String pay() {
		
		return "pay";
	}
	
	@GetMapping("/payment-success")
	public String paymentSuccess(HttpSession session) {
	    String mail = (String) session.getAttribute("email");
	    System.out.println("mail: "+mail);

	    // Check if mail is not null before proceeding
	    if (mail != null) {
	        Users u = service.getUser(mail);
	        System.out.println(u);

	        // Check if u is not null before calling methods on it
	        if (u != null) {
	            u.setPremium(true);
	            service.updateUser(u);
	            return "customerHome";
	        } else {
	            // Handle the case when u is null
	        	System.out.println("User not found");
	            return "User not found";
	        }
	    } else {
	        // Handle the case when mail is null
	    	System.out.println("Email not found in session");
	        return "Email not found in session";
	    }
	}
	
	@GetMapping("/payment-failure")
	public String paymentFailure() {
		return "customerHome";
	}

	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {

		int  amount  = 5000;
		Order order=null;
		try {
			RazorpayClient razorpay=new RazorpayClient("rzp_test_UqBICcJOEJD4nH", "J3vxuNudUteoWa9zX7UDoEmd");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount*100); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");

			order = razorpay.orders.create(orderRequest);

			

		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		finally {
			return order.toString();
		}
	}	
	
	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestBody Map<String, String> requestBody) {
	    try {
	    	String orderId = requestBody.get("orderId");
	        String paymentId = requestBody.get("paymentId");
	        String signature = requestBody.get("signature");
	        
	        // Initialize Razorpay client with your API key and secret
	        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_UqBICcJOEJD4nH", "J3vxuNudUteoWa9zX7UDoEmd");
	        // Create a signature verification data string
	        String verificationData = orderId + "|" + paymentId;

	        // Use Razorpay's utility function to verify the signature
	        boolean isValidSignature = Utils.verifySignature(verificationData, signature, "J3vxuNudUteoWa9zX7UDoEmd");

	        return isValidSignature;
	    } catch (RazorpayException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}