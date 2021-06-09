package com.abinanth.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abinanth.exception.DBException;
import com.abinanth.model.PaymentModel;
import com.abinanth.services.PaymentService;
import com.abinanth.util.Logger;

/**
 * Servlet implementation class DisplayAllDetailsServlet
 */
@WebServlet("/DisplayAllDetailsServlet")
public class DisplayAllDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger log = new Logger();
		HttpSession session = request.getSession();
		// int finalAmount=0;
		log.print("############### Display servlet works#################");

		String recidencyNo = (String) session.getAttribute("recidencyNo");
		log.print(recidencyNo);
		String finalAmount = (String) session.getAttribute("amount");
		String recidencyType = (String) session.getAttribute("recidencyType");
		String status = (String) session.getAttribute("status");
		PaymentModel payment = new PaymentModel(recidencyNo, recidencyType, finalAmount, status);
		payment.setRecidencyNo(recidencyNo);
		payment.setRecidencyType(recidencyType);
		payment.setAmount(finalAmount);
		payment.setStatus(status);
		boolean isCorrect = PaymentService.addPaymentDetails(payment);

		if (isCorrect) {
			String message = "Successfully Added";
			response.sendRedirect("DisplayAllDetails.jsp?message=" + message);
		} else {
			String errorMessage = "Unable to update status";
			response.sendRedirect("BillGenerator.jsp?errorMessage=" + errorMessage);
		}
	}

}
