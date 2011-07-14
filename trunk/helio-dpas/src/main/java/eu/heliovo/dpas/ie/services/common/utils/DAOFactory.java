package eu.heliovo.dpas.ie.services.common.utils;


import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.soda.dao.interfaces.SoteriaQueryDao;
import eu.heliovo.dpas.ie.services.hqi.dao.interfaces.HqiQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;

public abstract class DAOFactory {
	
	  public abstract VsoQueryDao getVsoQueryDao();
	  public abstract HqiQueryDao getHqiQueryDao();
	  public abstract CdaWebQueryDao getCdaWebQueryDao();
	  public abstract DirQueryDao getDirQueryDao();
	  
	  public static DPASDataProvider getDAOFactory(
	    String whichFactory) {
		Type type=Type.valueOf(whichFactory);
	    switch (type) {
	      case VSO: 
	          return (VsoQueryDao)CommonDaoFactory.getInstance().getVsoQueryDao();
	      case CDAWEB    : 
	          return (CdaWebQueryDao)CommonDaoFactory.getInstance().getCdaWebQueryDao();      
	      case DIR    :   
	          return (DirQueryDao)CommonDaoFactory.getInstance().getDirQueryDao();
	      case UOC		:
	    	  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
	      case SODA		:
	    	  return (SoteriaQueryDao)CommonDaoFactory.getInstance().getSoteriaQueryDao();
	      default        : 
	          return null;
	    }
	  }
	   	  
	  enum Type{
		  VSO,CDAWEB,DIR,UOC,SODA
		};
}
