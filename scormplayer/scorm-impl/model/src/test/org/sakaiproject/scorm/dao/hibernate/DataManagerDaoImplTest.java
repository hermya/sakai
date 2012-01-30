package org.sakaiproject.scorm.dao.hibernate;

import org.adl.datamodels.DMErrorCodes;
import org.adl.datamodels.DMFactory;
import org.adl.datamodels.DMProcessingInfo;
import org.adl.datamodels.DMRequest;
import org.adl.datamodels.SCODataManager;
import org.adl.datamodels.ieee.ValidatorFactory;
import org.sakaiproject.scorm.dao.api.DataManagerDao;

public class DataManagerDaoImplTest extends AbstractServiceTest {
	DataManagerDao dataManagerDao;

	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		dataManagerDao = (DataManagerDao) getApplicationContext().getBean("org.sakaiproject.scorm.dao.api.DataManagerDao");
	}

	public void testSimple() {

		SCODataManager dataManager = new SCODataManager();
		dataManager.setValidatorFactory(new ValidatorFactory());
		dataManager.addDM(DMFactory.DM_SCORM_2004);
		dataManager.addDM(DMFactory.DM_SCORM_NAV);
		dataManager.addDM(DMFactory.DM_SSP);
		dataManagerDao.save(dataManager);
		dataManager.setValue(new DMRequest("cmi.interactions.0.id", "1"));

		dataManagerDao.update(dataManager);
		
		assertEquals(0, dataManager.getValue(new DMRequest("cmi.interactions.0.id"), new DMProcessingInfo()));
		assertEquals(DMErrorCodes.OUT_OF_RANGE, dataManager.getValue(new DMRequest("cmi.interactions.1.id"), new DMProcessingInfo()));
	}

}
