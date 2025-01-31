package com.abinanth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abinanth.exception.InputMissMatchException;
import com.abinanth.exception.ValidationException;
import com.abinanth.model.BillCalculatorModel;
import com.abinanth.model.PaymentModel;
import com.abinanth.services.BillCalculatorService;
import com.abinanth.services.BillGeneratorService;
import com.abinanth.services.PaymentService;
import com.abinanth.util.Logger;

/**
 * Servlet implementation class SelectRecidencyType
 */
@WebServlet("/BillCalculator")
public class BillCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger log = new Logger();
		log.print("############ Bill calculator started###########");
		String recidencyType = request.getParameter("recidencyType");
		String recidencyNo = request.getParameter("recidencyNo");
		String streetName = request.getParameter("streetName");
		String cityName = request.getParameter("cityName");
		String district = request.getParameter("districtName");
		String year = request.getParameter("date");

		BillCalculatorModel bill = new BillCalculatorModel();
		bill.setRecidenyType(recidencyType);
		bill.setRecidencyNo(recidencyNo);
		bill.setStreetName(streetName);
		bill.setCityName(cityName);
		bill.setDistrict(district);
		bill.setYear(year);

		boolean flag = BillCalculatorService.addRecidencyDetails(bill);
		HttpSession session = request.getSession();
		String amount = null;
		try {

			if (flag) {
				log.print("Year=" + year + ",recidencyType=" + recidencyType);
				amount = BillGeneratorService.generateBill(recidencyType, year);
				session.setAttribute("amount", amount);
				session.setAttribute("recidencyType", recidencyType);
				session.setAttribute("recidencyNo", recidencyNo);
				PaymentModel payment = new PaymentModel();
				payment.setRecidencyNo(recidencyNo);
				payment.setRecidencyType(recidencyType);
				payment.setAmount(amount);
				String username = (String) session.getAttribute("LOGGED_IN_USER");
				payment.setUsername(username);
				boolean isCorrect = PaymentService.addPaymentDetails(payment);
				log.print(isCorrect);
				String message = "Bill Generated Successfully";
				response.sendRedirect("BillCalculator.jsp?message=" + message);
			} else {
				String errorMessage = "Unable To Add  Input Credentials";
				session.setAttribute("errorMessage", errorMessage);
				response.sendRedirect("BillCalculator.jsp?errorMessage=" + errorMessage);

			}

		} catch (ValidationException | InputMissMatchException e) {
			e.printStackTrace();

			response.sendRedirect("BillCalculator.jsp?errorMessage=" + e.getMessage());

		}
	}

}
