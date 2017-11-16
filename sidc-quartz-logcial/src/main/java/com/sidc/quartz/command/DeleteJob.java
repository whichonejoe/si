package com.sidc.quartz.command;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.sidc.common.framework.abs.AbstractAPIProcess;
import com.sidc.quartz.api.request.ScheduleCommandRequest;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;
import com.sidc.utils.status.APIStatus;

public class DeleteJob extends AbstractAPIProcess {

	private ScheduleCommandRequest enity;
	
	public DeleteJob(ScheduleCommandRequest enity) {
		// TODO Auto-generated constructor stub
		this.enity = enity;
	}
	
	@Override
	protected void init() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().debug("Request:" + this.enity);
	}

	@Override
	protected Object process() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobKey jobKey = JobKey.jobKey(this.enity.getJobname(), this.enity.getGroupname());
			// scheduler.deleteJob(jobKey);
			new PauseJob(this.enity).execute();
			scheduler.unscheduleJob(TriggerKey.triggerKey(this.enity.getJobname(), this.enity.getGroupname()));
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			LogAction.getInstance().error("DeleteJob Error:", e);
		}
		return null;
	}

	@Override
	protected void check() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		if (this.enity == null) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Rquest is illegal");
		}
		
		if (StringUtils.isBlank(this.enity.getJobname())) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Job name is empty");
		}
		
		if (StringUtils.isBlank(this.enity.getGroupname())) {
			throw new SiDCException(APIStatus.ILLEGAL_ARGUMENT, "Group name is empty");
		}
	}

}
