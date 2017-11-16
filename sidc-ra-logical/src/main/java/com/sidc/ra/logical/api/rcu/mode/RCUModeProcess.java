package com.sidc.ra.logical.api.rcu.mode;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.sidc.common.framework.abs.AbstractAPIProcess;
import com.sidc.configuration.blackcore.SidcUrlsConfiguration;
import com.sidc.configuration.blackcore.SidcUrlsLink;
import com.sidc.configuration.common.key.CommonCatalogueRCUKey;
import com.sidc.configuration.conf.SidcUrlName;
import com.sidc.dao.ra.manager.RcuModeManager;
import com.sidc.dao.rcu.command.response.RcuModel;
import com.sidc.dao.rcu.command.response.RcuModelCatalogue;
import com.sidc.dao.rcu.command.response.RcuModelCondition;
import com.sidc.dao.rcu.command.response.RcuModelDevice;
import com.sidc.dao.rcu.command.response.RcuRoomMode;
import com.sidc.rcu.connector.bean.command.AirConditionCommander;
import com.sidc.rcu.connector.bean.command.BulbCommander;
import com.sidc.rcu.connector.bean.command.RCUCommander;
import com.sidc.rcu.connector.bean.command.ServiceCommander;
import com.sidc.rcu.sdk.engine.RCUCommandClient;
import com.sidc.utils.common.DataCenter;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;
import com.sidc.utils.status.APIStatus;

/**
 * 
 * @author Allen Huang
 *
 */
public class RCUModeProcess extends AbstractAPIProcess {

	private final RcuRoomMode enity;
	private final String step = "2";

	public RCUModeProcess(RcuRoomMode enity) {
		// TODO Auto-generated constructor stub
		this.enity = enity;
	}

	@Override
	protected void init() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().debug("Request:" + this.enity);
		LogAction.getInstance().setUserId(this.enity.getRoomno());
		LogAction.getInstance().setContent(this.enity.getMode());
	}

	@Override
	protected Object process() throws SiDCException, Exception {
		// TODO Auto-generated method stub

		final Gson gson = new Gson();

		StringBuilder builder = new StringBuilder(
				RcuModeManager.getInstance().selectModeCentent(this.enity.getMode(), this.enity.getRoomno()));

		if (StringUtils.isBlank(builder)) {
			throw new SiDCException(APIStatus.DATA_DOES_NOT_EXIST, "not find group mode.");
		}

		final RcuModel model = gson.fromJson(builder.toString(), RcuModel.class);

		for (final RcuModelCatalogue catalogues : model.getList()) {
			final String catalogue = catalogues.getCatalogue();
			final List<RcuModelDevice> devices = catalogues.getDevices();

			RCUCommander commander = null;
			for (final RcuModelDevice device : devices) {
				RcuModelCondition condition = device.getCondition();
				if (catalogue.equals(CommonCatalogueRCUKey.AIR_CONDITION)) {
					AirConditionCommander hvac = new AirConditionCommander(condition.getAddress());
					hvac.setPower(condition.isPower());
					hvac.setFunction(condition.getFunction());
					hvac.setTemperature(condition.getTemperature());
					hvac.setSpeed(condition.getSpeed());
					hvac.setTimer(condition.getTimer());
					commander = new RCUCommander(UUID.randomUUID().toString(), this.enity.getRoomno(),
							device.getKeycode(), hvac);
				}
				if (catalogue.equals(CommonCatalogueRCUKey.BULB)) {
					BulbCommander bulb = new BulbCommander(condition.getStatus());
					commander = new RCUCommander(UUID.randomUUID().toString(), this.enity.getRoomno(),
							device.getKeycode(), bulb);
				}
				if (catalogue.equals(CommonCatalogueRCUKey.SERVICE)) {
					ServiceCommander service = new ServiceCommander(condition.getStatus());
					commander = new RCUCommander(UUID.randomUUID().toString(), this.enity.getRoomno(),
							device.getKeycode(), service);
				}

				new RCUCommandClient<Object>(configure(SidcUrlName.RCUENGINE.toString()), commander).execute();
			}
		}
		LogAction.getInstance().debug("step 2/" + step + ":send command success.");
		return null;
	}

	@Override
	protected void check() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		if (this.enity == null) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Rquest is illegal");
		}
		if (StringUtils.isBlank(this.enity.getRoomno())) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Rquest is illegal(roomNo)");
		}
		if (StringUtils.isBlank(this.enity.getMode())) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Rquest is illegal(mode)");
		}
	}

	public String configure(final String serverName) throws SiDCException {
		SidcUrlsConfiguration sidcConfigure = (SidcUrlsConfiguration) DataCenter.getInstance()
				.get(SidcUrlName.SITS.toString());
		List<SidcUrlsLink> urlsLinks = sidcConfigure.getUrls();
		String url = null;
		for (SidcUrlsLink sidcUrlsLink : urlsLinks) {
			if (sidcUrlsLink.getName().equalsIgnoreCase(serverName))
				url = sidcUrlsLink.getUrl();
		}
		return url;
	}

}
