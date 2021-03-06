package com.sidc.blackcore.servlet.movie;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.blackcore.api.parser.APIParser;
import com.sidc.blackcore.api.parser.APIServlet;
import com.sidc.blackcore.api.sits.movie.request.MovieListRequest;
import com.sidc.sits.logical.movie.MovieListProcess;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

@WebServlet("/sits/movielist")
public class MovieListServlet extends APIServlet {
	private static final long serialVersionUID = 8754393126264138813L;
	private final static Logger logger = LoggerFactory.getLogger(MovieListServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected Object execute(String apiRequest, HttpServletRequest req) throws SiDCException, Exception {
		final MovieListRequest enity = (MovieListRequest) APIParser.getInstance().parse(apiRequest,
				MovieListRequest.class);

		return new MovieListProcess(enity).executeWithMobileToken();
	}

	@Override
	protected void initial() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}
}
