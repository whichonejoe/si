package com.sidc.blackcore.servlet.api.rcu.command;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.blackcore.api.parser.APIParser;
import com.sidc.blackcore.api.parser.APIServlet;
import com.sidc.blackcore.api.ra.command.request.MobileCommanderRequeset;
import com.sidc.ra.logical.api.rcu.command.RCUServiceCommandProcess;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

/**
 * 
 * @author Allen Huang
 *
 */
@WebServlet("/rcu/servicecommand")
public class ServiceComandServlet extends APIServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2870168654599716849L;
	private final static Logger logger = LoggerFactory.getLogger(ServiceComandServlet.class);

	public ServiceComandServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object execute(String apiRequest, HttpServletRequest req) throws SiDCException, Exception {
		// TODO Auto-generated method stub
		
		MobileCommanderRequeset request = (MobileCommanderRequeset) APIParser.getInstance().parse(apiRequest,
				MobileCommanderRequeset.class);

		return new RCUServiceCommandProcess(request, req.getLocalAddr()).executeWithMobileToken();
	}

	@Override
	protected void initial() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}

}
