package com.sidc.blackcore.servlet.shop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.blackcore.api.parser.APIParser;
import com.sidc.blackcore.api.parser.APIServlet;
import com.sidc.blackcore.api.sits.shop.request.ShoppingBackendOrderListRequest;
import com.sidc.sits.logical.shopping.ShoppingBackendOrderListProcess;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

@WebServlet("/sits/backend/shopping/orderlist")
public class ShoppingBackendOrderListServlet extends APIServlet {
	private static final long serialVersionUID = -662658578732670418L;
	private final static Logger logger = LoggerFactory.getLogger(ShoppingBackendOrderListServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected Object execute(String apiRequest, HttpServletRequest req) throws SiDCException, Exception {
		// TODO Auto-generated method stub
		final ShoppingBackendOrderListRequest request = (ShoppingBackendOrderListRequest) APIParser.getInstance()
				.parse(apiRequest, ShoppingBackendOrderListRequest.class);

		return new ShoppingBackendOrderListProcess(request).executeByAuthToken(req.getServletPath());
	}

	@Override
	protected void initial() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}

}
