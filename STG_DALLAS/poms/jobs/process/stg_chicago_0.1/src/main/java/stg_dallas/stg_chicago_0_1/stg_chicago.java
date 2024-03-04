
package stg_dallas.stg_chicago_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: stg_chicago Purpose: <br>
 * Description: <br>
 * 
 * @author verma.sidd@northeastern.edu
 * @version 8.0.1.20231017_1026-patch
 * @status
 */
public class stg_chicago implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "stg_chicago.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(stg_chicago.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "stg_chicago";
	private final String projectName = "STG_DALLAS";
	public Integer errorCode = null;
	private String currentComponent = "";

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_30lioNQeEe6gxbGc2yFGoQ", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					stg_chicago.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(stg_chicago.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class load_stg_dallasStruct implements routines.system.IPersistableRow<load_stg_dallasStruct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_stg_chicago = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_stg_chicago = new byte[0];

		public String Inspection_ID;

		public String getInspection_ID() {
			return this.Inspection_ID;
		}

		public Boolean Inspection_IDIsNullable() {
			return true;
		}

		public Boolean Inspection_IDIsKey() {
			return false;
		}

		public Integer Inspection_IDLength() {
			return 10;
		}

		public Integer Inspection_IDPrecision() {
			return 0;
		}

		public String Inspection_IDDefault() {

			return null;

		}

		public String Inspection_IDComment() {

			return "";

		}

		public String Inspection_IDPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_IDOriginalDbColumnName() {

			return "Inspection_ID";

		}

		public String Restaurant_Name;

		public String getRestaurant_Name() {
			return this.Restaurant_Name;
		}

		public Boolean Restaurant_NameIsNullable() {
			return true;
		}

		public Boolean Restaurant_NameIsKey() {
			return false;
		}

		public Integer Restaurant_NameLength() {
			return 65;
		}

		public Integer Restaurant_NamePrecision() {
			return 0;
		}

		public String Restaurant_NameDefault() {

			return null;

		}

		public String Restaurant_NameComment() {

			return "";

		}

		public String Restaurant_NamePattern() {

			return "dd-MM-yyyy";

		}

		public String Restaurant_NameOriginalDbColumnName() {

			return "Restaurant_Name";

		}

		public String Inspection_Type;

		public String getInspection_Type() {
			return this.Inspection_Type;
		}

		public Boolean Inspection_TypeIsNullable() {
			return true;
		}

		public Boolean Inspection_TypeIsKey() {
			return false;
		}

		public Integer Inspection_TypeLength() {
			return 9;
		}

		public Integer Inspection_TypePrecision() {
			return 0;
		}

		public String Inspection_TypeDefault() {

			return null;

		}

		public String Inspection_TypeComment() {

			return "";

		}

		public String Inspection_TypePattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_TypeOriginalDbColumnName() {

			return "Inspection_Type";

		}

		public java.util.Date Inspection_Date;

		public java.util.Date getInspection_Date() {
			return this.Inspection_Date;
		}

		public Boolean Inspection_DateIsNullable() {
			return true;
		}

		public Boolean Inspection_DateIsKey() {
			return false;
		}

		public Integer Inspection_DateLength() {
			return 300;
		}

		public Integer Inspection_DatePrecision() {
			return 0;
		}

		public String Inspection_DateDefault() {

			return null;

		}

		public String Inspection_DateComment() {

			return "";

		}

		public String Inspection_DatePattern() {

			return "MM-dd-yyyy";

		}

		public String Inspection_DateOriginalDbColumnName() {

			return "Inspection_Date";

		}

		public Integer Inspection_Score;

		public Integer getInspection_Score() {
			return this.Inspection_Score;
		}

		public Boolean Inspection_ScoreIsNullable() {
			return true;
		}

		public Boolean Inspection_ScoreIsKey() {
			return false;
		}

		public Integer Inspection_ScoreLength() {
			return 3;
		}

		public Integer Inspection_ScorePrecision() {
			return 0;
		}

		public String Inspection_ScoreDefault() {

			return null;

		}

		public String Inspection_ScoreComment() {

			return "";

		}

		public String Inspection_ScorePattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_ScoreOriginalDbColumnName() {

			return "Inspection_Score";

		}

		public Integer Street_Number;

		public Integer getStreet_Number() {
			return this.Street_Number;
		}

		public Boolean Street_NumberIsNullable() {
			return true;
		}

		public Boolean Street_NumberIsKey() {
			return false;
		}

		public Integer Street_NumberLength() {
			return 5;
		}

		public Integer Street_NumberPrecision() {
			return 0;
		}

		public String Street_NumberDefault() {

			return null;

		}

		public String Street_NumberComment() {

			return "";

		}

		public String Street_NumberPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_NumberOriginalDbColumnName() {

			return "Street_Number";

		}

		public String Street_Name;

		public String getStreet_Name() {
			return this.Street_Name;
		}

		public Boolean Street_NameIsNullable() {
			return true;
		}

		public Boolean Street_NameIsKey() {
			return false;
		}

		public Integer Street_NameLength() {
			return 25;
		}

		public Integer Street_NamePrecision() {
			return 0;
		}

		public String Street_NameDefault() {

			return null;

		}

		public String Street_NameComment() {

			return "";

		}

		public String Street_NamePattern() {

			return "dd-MM-yyyy";

		}

		public String Street_NameOriginalDbColumnName() {

			return "Street_Name";

		}

		public String Street_Direction;

		public String getStreet_Direction() {
			return this.Street_Direction;
		}

		public Boolean Street_DirectionIsNullable() {
			return true;
		}

		public Boolean Street_DirectionIsKey() {
			return false;
		}

		public Integer Street_DirectionLength() {
			return 3;
		}

		public Integer Street_DirectionPrecision() {
			return 0;
		}

		public String Street_DirectionDefault() {

			return null;

		}

		public String Street_DirectionComment() {

			return "";

		}

		public String Street_DirectionPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_DirectionOriginalDbColumnName() {

			return "Street_Direction";

		}

		public String Street_Type;

		public String getStreet_Type() {
			return this.Street_Type;
		}

		public Boolean Street_TypeIsNullable() {
			return true;
		}

		public Boolean Street_TypeIsKey() {
			return false;
		}

		public Integer Street_TypeLength() {
			return 4;
		}

		public Integer Street_TypePrecision() {
			return 0;
		}

		public String Street_TypeDefault() {

			return null;

		}

		public String Street_TypeComment() {

			return "";

		}

		public String Street_TypePattern() {

			return "dd-MM-yyyy";

		}

		public String Street_TypeOriginalDbColumnName() {

			return "Street_Type";

		}

		public String Street_Unit;

		public String getStreet_Unit() {
			return this.Street_Unit;
		}

		public Boolean Street_UnitIsNullable() {
			return true;
		}

		public Boolean Street_UnitIsKey() {
			return false;
		}

		public Integer Street_UnitLength() {
			return 5;
		}

		public Integer Street_UnitPrecision() {
			return 0;
		}

		public String Street_UnitDefault() {

			return null;

		}

		public String Street_UnitComment() {

			return "";

		}

		public String Street_UnitPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_UnitOriginalDbColumnName() {

			return "Street_Unit";

		}

		public String Street_Address;

		public String getStreet_Address() {
			return this.Street_Address;
		}

		public Boolean Street_AddressIsNullable() {
			return true;
		}

		public Boolean Street_AddressIsKey() {
			return false;
		}

		public Integer Street_AddressLength() {
			return 37;
		}

		public Integer Street_AddressPrecision() {
			return 0;
		}

		public String Street_AddressDefault() {

			return null;

		}

		public String Street_AddressComment() {

			return "";

		}

		public String Street_AddressPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_AddressOriginalDbColumnName() {

			return "Street_Address";

		}

		public String Zip_Code;

		public String getZip_Code() {
			return this.Zip_Code;
		}

		public Boolean Zip_CodeIsNullable() {
			return true;
		}

		public Boolean Zip_CodeIsKey() {
			return false;
		}

		public Integer Zip_CodeLength() {
			return 10;
		}

		public Integer Zip_CodePrecision() {
			return 0;
		}

		public String Zip_CodeDefault() {

			return null;

		}

		public String Zip_CodeComment() {

			return "";

		}

		public String Zip_CodePattern() {

			return "dd-MM-yyyy";

		}

		public String Zip_CodeOriginalDbColumnName() {

			return "Zip_Code";

		}

		public Double Violation_Points___1;

		public Double getViolation_Points___1() {
			return this.Violation_Points___1;
		}

		public Boolean Violation_Points___1IsNullable() {
			return true;
		}

		public Boolean Violation_Points___1IsKey() {
			return false;
		}

		public Integer Violation_Points___1Length() {
			return 3;
		}

		public Integer Violation_Points___1Precision() {
			return 0;
		}

		public String Violation_Points___1Default() {

			return null;

		}

		public String Violation_Points___1Comment() {

			return "";

		}

		public String Violation_Points___1Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___1OriginalDbColumnName() {

			return "Violation_Points___1";

		}

		public Double Violation_Points___2;

		public Double getViolation_Points___2() {
			return this.Violation_Points___2;
		}

		public Boolean Violation_Points___2IsNullable() {
			return true;
		}

		public Boolean Violation_Points___2IsKey() {
			return false;
		}

		public Integer Violation_Points___2Length() {
			return 3;
		}

		public Integer Violation_Points___2Precision() {
			return 0;
		}

		public String Violation_Points___2Default() {

			return null;

		}

		public String Violation_Points___2Comment() {

			return "";

		}

		public String Violation_Points___2Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___2OriginalDbColumnName() {

			return "Violation_Points___2";

		}

		public Double Violation_Points___3;

		public Double getViolation_Points___3() {
			return this.Violation_Points___3;
		}

		public Boolean Violation_Points___3IsNullable() {
			return true;
		}

		public Boolean Violation_Points___3IsKey() {
			return false;
		}

		public Integer Violation_Points___3Length() {
			return 3;
		}

		public Integer Violation_Points___3Precision() {
			return 0;
		}

		public String Violation_Points___3Default() {

			return null;

		}

		public String Violation_Points___3Comment() {

			return "";

		}

		public String Violation_Points___3Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___3OriginalDbColumnName() {

			return "Violation_Points___3";

		}

		public Double Violation_Points___4;

		public Double getViolation_Points___4() {
			return this.Violation_Points___4;
		}

		public Boolean Violation_Points___4IsNullable() {
			return true;
		}

		public Boolean Violation_Points___4IsKey() {
			return false;
		}

		public Integer Violation_Points___4Length() {
			return 3;
		}

		public Integer Violation_Points___4Precision() {
			return 0;
		}

		public String Violation_Points___4Default() {

			return null;

		}

		public String Violation_Points___4Comment() {

			return "";

		}

		public String Violation_Points___4Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___4OriginalDbColumnName() {

			return "Violation_Points___4";

		}

		public Double Violation_Points___5;

		public Double getViolation_Points___5() {
			return this.Violation_Points___5;
		}

		public Boolean Violation_Points___5IsNullable() {
			return true;
		}

		public Boolean Violation_Points___5IsKey() {
			return false;
		}

		public Integer Violation_Points___5Length() {
			return 3;
		}

		public Integer Violation_Points___5Precision() {
			return 0;
		}

		public String Violation_Points___5Default() {

			return null;

		}

		public String Violation_Points___5Comment() {

			return "";

		}

		public String Violation_Points___5Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___5OriginalDbColumnName() {

			return "Violation_Points___5";

		}

		public Double Violation_Points___6;

		public Double getViolation_Points___6() {
			return this.Violation_Points___6;
		}

		public Boolean Violation_Points___6IsNullable() {
			return true;
		}

		public Boolean Violation_Points___6IsKey() {
			return false;
		}

		public Integer Violation_Points___6Length() {
			return 3;
		}

		public Integer Violation_Points___6Precision() {
			return 0;
		}

		public String Violation_Points___6Default() {

			return null;

		}

		public String Violation_Points___6Comment() {

			return "";

		}

		public String Violation_Points___6Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___6OriginalDbColumnName() {

			return "Violation_Points___6";

		}

		public Double Violation_Points___7;

		public Double getViolation_Points___7() {
			return this.Violation_Points___7;
		}

		public Boolean Violation_Points___7IsNullable() {
			return true;
		}

		public Boolean Violation_Points___7IsKey() {
			return false;
		}

		public Integer Violation_Points___7Length() {
			return 3;
		}

		public Integer Violation_Points___7Precision() {
			return 0;
		}

		public String Violation_Points___7Default() {

			return null;

		}

		public String Violation_Points___7Comment() {

			return "";

		}

		public String Violation_Points___7Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___7OriginalDbColumnName() {

			return "Violation_Points___7";

		}

		public Double Violation_Points___8;

		public Double getViolation_Points___8() {
			return this.Violation_Points___8;
		}

		public Boolean Violation_Points___8IsNullable() {
			return true;
		}

		public Boolean Violation_Points___8IsKey() {
			return false;
		}

		public Integer Violation_Points___8Length() {
			return 3;
		}

		public Integer Violation_Points___8Precision() {
			return 0;
		}

		public String Violation_Points___8Default() {

			return null;

		}

		public String Violation_Points___8Comment() {

			return "";

		}

		public String Violation_Points___8Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___8OriginalDbColumnName() {

			return "Violation_Points___8";

		}

		public Double Violation_Points___9;

		public Double getViolation_Points___9() {
			return this.Violation_Points___9;
		}

		public Boolean Violation_Points___9IsNullable() {
			return true;
		}

		public Boolean Violation_Points___9IsKey() {
			return false;
		}

		public Integer Violation_Points___9Length() {
			return 3;
		}

		public Integer Violation_Points___9Precision() {
			return 0;
		}

		public String Violation_Points___9Default() {

			return null;

		}

		public String Violation_Points___9Comment() {

			return "";

		}

		public String Violation_Points___9Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___9OriginalDbColumnName() {

			return "Violation_Points___9";

		}

		public Double Violation_Points___10;

		public Double getViolation_Points___10() {
			return this.Violation_Points___10;
		}

		public Boolean Violation_Points___10IsNullable() {
			return true;
		}

		public Boolean Violation_Points___10IsKey() {
			return false;
		}

		public Integer Violation_Points___10Length() {
			return 3;
		}

		public Integer Violation_Points___10Precision() {
			return 0;
		}

		public String Violation_Points___10Default() {

			return null;

		}

		public String Violation_Points___10Comment() {

			return "";

		}

		public String Violation_Points___10Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___10OriginalDbColumnName() {

			return "Violation_Points___10";

		}

		public Double Violation_Points___11;

		public Double getViolation_Points___11() {
			return this.Violation_Points___11;
		}

		public Boolean Violation_Points___11IsNullable() {
			return true;
		}

		public Boolean Violation_Points___11IsKey() {
			return false;
		}

		public Integer Violation_Points___11Length() {
			return 3;
		}

		public Integer Violation_Points___11Precision() {
			return 0;
		}

		public String Violation_Points___11Default() {

			return null;

		}

		public String Violation_Points___11Comment() {

			return "";

		}

		public String Violation_Points___11Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___11OriginalDbColumnName() {

			return "Violation_Points___11";

		}

		public Double Violation_Points___12;

		public Double getViolation_Points___12() {
			return this.Violation_Points___12;
		}

		public Boolean Violation_Points___12IsNullable() {
			return true;
		}

		public Boolean Violation_Points___12IsKey() {
			return false;
		}

		public Integer Violation_Points___12Length() {
			return 3;
		}

		public Integer Violation_Points___12Precision() {
			return 0;
		}

		public String Violation_Points___12Default() {

			return null;

		}

		public String Violation_Points___12Comment() {

			return "";

		}

		public String Violation_Points___12Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___12OriginalDbColumnName() {

			return "Violation_Points___12";

		}

		public Double Violation_Points___13;

		public Double getViolation_Points___13() {
			return this.Violation_Points___13;
		}

		public Boolean Violation_Points___13IsNullable() {
			return true;
		}

		public Boolean Violation_Points___13IsKey() {
			return false;
		}

		public Integer Violation_Points___13Length() {
			return 3;
		}

		public Integer Violation_Points___13Precision() {
			return 0;
		}

		public String Violation_Points___13Default() {

			return null;

		}

		public String Violation_Points___13Comment() {

			return "";

		}

		public String Violation_Points___13Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___13OriginalDbColumnName() {

			return "Violation_Points___13";

		}

		public Double Violation_Points___14;

		public Double getViolation_Points___14() {
			return this.Violation_Points___14;
		}

		public Boolean Violation_Points___14IsNullable() {
			return true;
		}

		public Boolean Violation_Points___14IsKey() {
			return false;
		}

		public Integer Violation_Points___14Length() {
			return 3;
		}

		public Integer Violation_Points___14Precision() {
			return 0;
		}

		public String Violation_Points___14Default() {

			return null;

		}

		public String Violation_Points___14Comment() {

			return "";

		}

		public String Violation_Points___14Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___14OriginalDbColumnName() {

			return "Violation_Points___14";

		}

		public Double Violation_Points___15;

		public Double getViolation_Points___15() {
			return this.Violation_Points___15;
		}

		public Boolean Violation_Points___15IsNullable() {
			return true;
		}

		public Boolean Violation_Points___15IsKey() {
			return false;
		}

		public Integer Violation_Points___15Length() {
			return 3;
		}

		public Integer Violation_Points___15Precision() {
			return 0;
		}

		public String Violation_Points___15Default() {

			return null;

		}

		public String Violation_Points___15Comment() {

			return "";

		}

		public String Violation_Points___15Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___15OriginalDbColumnName() {

			return "Violation_Points___15";

		}

		public Double Violation_Points___16;

		public Double getViolation_Points___16() {
			return this.Violation_Points___16;
		}

		public Boolean Violation_Points___16IsNullable() {
			return true;
		}

		public Boolean Violation_Points___16IsKey() {
			return false;
		}

		public Integer Violation_Points___16Length() {
			return 3;
		}

		public Integer Violation_Points___16Precision() {
			return 0;
		}

		public String Violation_Points___16Default() {

			return null;

		}

		public String Violation_Points___16Comment() {

			return "";

		}

		public String Violation_Points___16Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___16OriginalDbColumnName() {

			return "Violation_Points___16";

		}

		public Double Violation_Points___17;

		public Double getViolation_Points___17() {
			return this.Violation_Points___17;
		}

		public Boolean Violation_Points___17IsNullable() {
			return true;
		}

		public Boolean Violation_Points___17IsKey() {
			return false;
		}

		public Integer Violation_Points___17Length() {
			return 3;
		}

		public Integer Violation_Points___17Precision() {
			return 0;
		}

		public String Violation_Points___17Default() {

			return null;

		}

		public String Violation_Points___17Comment() {

			return "";

		}

		public String Violation_Points___17Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___17OriginalDbColumnName() {

			return "Violation_Points___17";

		}

		public Double Violation_Points___18;

		public Double getViolation_Points___18() {
			return this.Violation_Points___18;
		}

		public Boolean Violation_Points___18IsNullable() {
			return true;
		}

		public Boolean Violation_Points___18IsKey() {
			return false;
		}

		public Integer Violation_Points___18Length() {
			return 3;
		}

		public Integer Violation_Points___18Precision() {
			return 0;
		}

		public String Violation_Points___18Default() {

			return null;

		}

		public String Violation_Points___18Comment() {

			return "";

		}

		public String Violation_Points___18Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___18OriginalDbColumnName() {

			return "Violation_Points___18";

		}

		public Double Violation_Points___19;

		public Double getViolation_Points___19() {
			return this.Violation_Points___19;
		}

		public Boolean Violation_Points___19IsNullable() {
			return true;
		}

		public Boolean Violation_Points___19IsKey() {
			return false;
		}

		public Integer Violation_Points___19Length() {
			return 3;
		}

		public Integer Violation_Points___19Precision() {
			return 0;
		}

		public String Violation_Points___19Default() {

			return null;

		}

		public String Violation_Points___19Comment() {

			return "";

		}

		public String Violation_Points___19Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___19OriginalDbColumnName() {

			return "Violation_Points___19";

		}

		public Double Violation_Points___20;

		public Double getViolation_Points___20() {
			return this.Violation_Points___20;
		}

		public Boolean Violation_Points___20IsNullable() {
			return true;
		}

		public Boolean Violation_Points___20IsKey() {
			return false;
		}

		public Integer Violation_Points___20Length() {
			return 3;
		}

		public Integer Violation_Points___20Precision() {
			return 0;
		}

		public String Violation_Points___20Default() {

			return null;

		}

		public String Violation_Points___20Comment() {

			return "";

		}

		public String Violation_Points___20Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___20OriginalDbColumnName() {

			return "Violation_Points___20";

		}

		public Double Violation_Points___21;

		public Double getViolation_Points___21() {
			return this.Violation_Points___21;
		}

		public Boolean Violation_Points___21IsNullable() {
			return true;
		}

		public Boolean Violation_Points___21IsKey() {
			return false;
		}

		public Integer Violation_Points___21Length() {
			return 3;
		}

		public Integer Violation_Points___21Precision() {
			return 0;
		}

		public String Violation_Points___21Default() {

			return null;

		}

		public String Violation_Points___21Comment() {

			return "";

		}

		public String Violation_Points___21Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___21OriginalDbColumnName() {

			return "Violation_Points___21";

		}

		public Double Violation_Points___22;

		public Double getViolation_Points___22() {
			return this.Violation_Points___22;
		}

		public Boolean Violation_Points___22IsNullable() {
			return true;
		}

		public Boolean Violation_Points___22IsKey() {
			return false;
		}

		public Integer Violation_Points___22Length() {
			return 3;
		}

		public Integer Violation_Points___22Precision() {
			return 0;
		}

		public String Violation_Points___22Default() {

			return null;

		}

		public String Violation_Points___22Comment() {

			return "";

		}

		public String Violation_Points___22Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___22OriginalDbColumnName() {

			return "Violation_Points___22";

		}

		public Double Violation_Points___23;

		public Double getViolation_Points___23() {
			return this.Violation_Points___23;
		}

		public Boolean Violation_Points___23IsNullable() {
			return true;
		}

		public Boolean Violation_Points___23IsKey() {
			return false;
		}

		public Integer Violation_Points___23Length() {
			return 3;
		}

		public Integer Violation_Points___23Precision() {
			return 0;
		}

		public String Violation_Points___23Default() {

			return null;

		}

		public String Violation_Points___23Comment() {

			return "";

		}

		public String Violation_Points___23Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___23OriginalDbColumnName() {

			return "Violation_Points___23";

		}

		public Double Violation_Points___24;

		public Double getViolation_Points___24() {
			return this.Violation_Points___24;
		}

		public Boolean Violation_Points___24IsNullable() {
			return true;
		}

		public Boolean Violation_Points___24IsKey() {
			return false;
		}

		public Integer Violation_Points___24Length() {
			return 3;
		}

		public Integer Violation_Points___24Precision() {
			return 0;
		}

		public String Violation_Points___24Default() {

			return null;

		}

		public String Violation_Points___24Comment() {

			return "";

		}

		public String Violation_Points___24Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___24OriginalDbColumnName() {

			return "Violation_Points___24";

		}

		public Double Violation_Points___25;

		public Double getViolation_Points___25() {
			return this.Violation_Points___25;
		}

		public Boolean Violation_Points___25IsNullable() {
			return true;
		}

		public Boolean Violation_Points___25IsKey() {
			return false;
		}

		public Integer Violation_Points___25Length() {
			return 3;
		}

		public Integer Violation_Points___25Precision() {
			return 0;
		}

		public String Violation_Points___25Default() {

			return "";

		}

		public String Violation_Points___25Comment() {

			return "";

		}

		public String Violation_Points___25Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___25OriginalDbColumnName() {

			return "Violation_Points___25";

		}

		public String Inspection_Month;

		public String getInspection_Month() {
			return this.Inspection_Month;
		}

		public Boolean Inspection_MonthIsNullable() {
			return true;
		}

		public Boolean Inspection_MonthIsKey() {
			return false;
		}

		public Integer Inspection_MonthLength() {
			return 8;
		}

		public Integer Inspection_MonthPrecision() {
			return 0;
		}

		public String Inspection_MonthDefault() {

			return null;

		}

		public String Inspection_MonthComment() {

			return "";

		}

		public String Inspection_MonthPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_MonthOriginalDbColumnName() {

			return "Inspection_Month";

		}

		public String Inspection_Year;

		public String getInspection_Year() {
			return this.Inspection_Year;
		}

		public Boolean Inspection_YearIsNullable() {
			return true;
		}

		public Boolean Inspection_YearIsKey() {
			return false;
		}

		public Integer Inspection_YearLength() {
			return 6;
		}

		public Integer Inspection_YearPrecision() {
			return 0;
		}

		public String Inspection_YearDefault() {

			return null;

		}

		public String Inspection_YearComment() {

			return "";

		}

		public String Inspection_YearPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_YearOriginalDbColumnName() {

			return "Inspection_Year";

		}

		public String Address;

		public String getAddress() {
			return this.Address;
		}

		public Boolean AddressIsNullable() {
			return true;
		}

		public Boolean AddressIsKey() {
			return false;
		}

		public Integer AddressLength() {
			return 250;
		}

		public Integer AddressPrecision() {
			return 0;
		}

		public String AddressDefault() {

			return null;

		}

		public String AddressComment() {

			return "";

		}

		public String AddressPattern() {

			return "dd-MM-yyyy";

		}

		public String AddressOriginalDbColumnName() {

			return "Address";

		}

		public BigDecimal Latitude;

		public BigDecimal getLatitude() {
			return this.Latitude;
		}

		public Boolean LatitudeIsNullable() {
			return true;
		}

		public Boolean LatitudeIsKey() {
			return false;
		}

		public Integer LatitudeLength() {
			return 65;
		}

		public Integer LatitudePrecision() {
			return 8;
		}

		public String LatitudeDefault() {

			return null;

		}

		public String LatitudeComment() {

			return "";

		}

		public String LatitudePattern() {

			return "dd-MM-yyyy";

		}

		public String LatitudeOriginalDbColumnName() {

			return "Latitude";

		}

		public BigDecimal Longitude;

		public BigDecimal getLongitude() {
			return this.Longitude;
		}

		public Boolean LongitudeIsNullable() {
			return true;
		}

		public Boolean LongitudeIsKey() {
			return false;
		}

		public Integer LongitudeLength() {
			return 65;
		}

		public Integer LongitudePrecision() {
			return 8;
		}

		public String LongitudeDefault() {

			return null;

		}

		public String LongitudeComment() {

			return "";

		}

		public String LongitudePattern() {

			return "dd-MM-yyyy";

		}

		public String LongitudeOriginalDbColumnName() {

			return "Longitude";

		}

		public java.util.Date DI_CreateDate;

		public java.util.Date getDI_CreateDate() {
			return this.DI_CreateDate;
		}

		public Boolean DI_CreateDateIsNullable() {
			return true;
		}

		public Boolean DI_CreateDateIsKey() {
			return false;
		}

		public Integer DI_CreateDateLength() {
			return null;
		}

		public Integer DI_CreateDatePrecision() {
			return null;
		}

		public String DI_CreateDateDefault() {

			return null;

		}

		public String DI_CreateDateComment() {

			return "";

		}

		public String DI_CreateDatePattern() {

			return "dd-MM-yyyy";

		}

		public String DI_CreateDateOriginalDbColumnName() {

			return "DI_CreateDate";

		}

		public String DI_WorkFlowFileNme;

		public String getDI_WorkFlowFileNme() {
			return this.DI_WorkFlowFileNme;
		}

		public Boolean DI_WorkFlowFileNmeIsNullable() {
			return true;
		}

		public Boolean DI_WorkFlowFileNmeIsKey() {
			return false;
		}

		public Integer DI_WorkFlowFileNmeLength() {
			return 300;
		}

		public Integer DI_WorkFlowFileNmePrecision() {
			return null;
		}

		public String DI_WorkFlowFileNmeDefault() {

			return null;

		}

		public String DI_WorkFlowFileNmeComment() {

			return "";

		}

		public String DI_WorkFlowFileNmePattern() {

			return "";

		}

		public String DI_WorkFlowFileNmeOriginalDbColumnName() {

			return "DI_WorkFlowFileNme";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_stg_chicago.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_stg_chicago.length == 0) {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_stg_chicago, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_stg_chicago, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_stg_chicago.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_stg_chicago.length == 0) {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_stg_chicago, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_stg_chicago, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_stg_chicago) {

				try {

					int length = 0;

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.Inspection_Type = readString(dis);

					this.Inspection_Date = readDate(dis);

					this.Inspection_Score = readInteger(dis);

					this.Street_Number = readInteger(dis);

					this.Street_Name = readString(dis);

					this.Street_Direction = readString(dis);

					this.Street_Type = readString(dis);

					this.Street_Unit = readString(dis);

					this.Street_Address = readString(dis);

					this.Zip_Code = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___1 = null;
					} else {
						this.Violation_Points___1 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___2 = null;
					} else {
						this.Violation_Points___2 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___3 = null;
					} else {
						this.Violation_Points___3 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___4 = null;
					} else {
						this.Violation_Points___4 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___5 = null;
					} else {
						this.Violation_Points___5 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___6 = null;
					} else {
						this.Violation_Points___6 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___7 = null;
					} else {
						this.Violation_Points___7 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___8 = null;
					} else {
						this.Violation_Points___8 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___9 = null;
					} else {
						this.Violation_Points___9 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___10 = null;
					} else {
						this.Violation_Points___10 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___11 = null;
					} else {
						this.Violation_Points___11 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___12 = null;
					} else {
						this.Violation_Points___12 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___13 = null;
					} else {
						this.Violation_Points___13 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___14 = null;
					} else {
						this.Violation_Points___14 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___15 = null;
					} else {
						this.Violation_Points___15 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___16 = null;
					} else {
						this.Violation_Points___16 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___17 = null;
					} else {
						this.Violation_Points___17 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___18 = null;
					} else {
						this.Violation_Points___18 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___19 = null;
					} else {
						this.Violation_Points___19 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___20 = null;
					} else {
						this.Violation_Points___20 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___21 = null;
					} else {
						this.Violation_Points___21 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___22 = null;
					} else {
						this.Violation_Points___22 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___23 = null;
					} else {
						this.Violation_Points___23 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___24 = null;
					} else {
						this.Violation_Points___24 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___25 = null;
					} else {
						this.Violation_Points___25 = dis.readDouble();
					}

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.Address = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

					this.DI_CreateDate = readDate(dis);

					this.DI_WorkFlowFileNme = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_stg_chicago) {

				try {

					int length = 0;

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.Inspection_Type = readString(dis);

					this.Inspection_Date = readDate(dis);

					this.Inspection_Score = readInteger(dis);

					this.Street_Number = readInteger(dis);

					this.Street_Name = readString(dis);

					this.Street_Direction = readString(dis);

					this.Street_Type = readString(dis);

					this.Street_Unit = readString(dis);

					this.Street_Address = readString(dis);

					this.Zip_Code = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___1 = null;
					} else {
						this.Violation_Points___1 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___2 = null;
					} else {
						this.Violation_Points___2 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___3 = null;
					} else {
						this.Violation_Points___3 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___4 = null;
					} else {
						this.Violation_Points___4 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___5 = null;
					} else {
						this.Violation_Points___5 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___6 = null;
					} else {
						this.Violation_Points___6 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___7 = null;
					} else {
						this.Violation_Points___7 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___8 = null;
					} else {
						this.Violation_Points___8 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___9 = null;
					} else {
						this.Violation_Points___9 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___10 = null;
					} else {
						this.Violation_Points___10 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___11 = null;
					} else {
						this.Violation_Points___11 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___12 = null;
					} else {
						this.Violation_Points___12 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___13 = null;
					} else {
						this.Violation_Points___13 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___14 = null;
					} else {
						this.Violation_Points___14 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___15 = null;
					} else {
						this.Violation_Points___15 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___16 = null;
					} else {
						this.Violation_Points___16 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___17 = null;
					} else {
						this.Violation_Points___17 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___18 = null;
					} else {
						this.Violation_Points___18 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___19 = null;
					} else {
						this.Violation_Points___19 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___20 = null;
					} else {
						this.Violation_Points___20 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___21 = null;
					} else {
						this.Violation_Points___21 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___22 = null;
					} else {
						this.Violation_Points___22 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___23 = null;
					} else {
						this.Violation_Points___23 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___24 = null;
					} else {
						this.Violation_Points___24 = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___25 = null;
					} else {
						this.Violation_Points___25 = dis.readDouble();
					}

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.Address = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

					this.DI_CreateDate = readDate(dis);

					this.DI_WorkFlowFileNme = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// java.util.Date

				writeDate(this.Inspection_Date, dos);

				// Integer

				writeInteger(this.Inspection_Score, dos);

				// Integer

				writeInteger(this.Street_Number, dos);

				// String

				writeString(this.Street_Name, dos);

				// String

				writeString(this.Street_Direction, dos);

				// String

				writeString(this.Street_Type, dos);

				// String

				writeString(this.Street_Unit, dos);

				// String

				writeString(this.Street_Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// Double

				if (this.Violation_Points___1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___1);
				}

				// Double

				if (this.Violation_Points___2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___2);
				}

				// Double

				if (this.Violation_Points___3 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___3);
				}

				// Double

				if (this.Violation_Points___4 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___4);
				}

				// Double

				if (this.Violation_Points___5 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___5);
				}

				// Double

				if (this.Violation_Points___6 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___6);
				}

				// Double

				if (this.Violation_Points___7 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___7);
				}

				// Double

				if (this.Violation_Points___8 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___8);
				}

				// Double

				if (this.Violation_Points___9 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___9);
				}

				// Double

				if (this.Violation_Points___10 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___10);
				}

				// Double

				if (this.Violation_Points___11 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___11);
				}

				// Double

				if (this.Violation_Points___12 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___12);
				}

				// Double

				if (this.Violation_Points___13 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___13);
				}

				// Double

				if (this.Violation_Points___14 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___14);
				}

				// Double

				if (this.Violation_Points___15 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___15);
				}

				// Double

				if (this.Violation_Points___16 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___16);
				}

				// Double

				if (this.Violation_Points___17 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___17);
				}

				// Double

				if (this.Violation_Points___18 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___18);
				}

				// Double

				if (this.Violation_Points___19 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___19);
				}

				// Double

				if (this.Violation_Points___20 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___20);
				}

				// Double

				if (this.Violation_Points___21 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___21);
				}

				// Double

				if (this.Violation_Points___22 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___22);
				}

				// Double

				if (this.Violation_Points___23 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___23);
				}

				// Double

				if (this.Violation_Points___24 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___24);
				}

				// Double

				if (this.Violation_Points___25 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___25);
				}

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.Address, dos);

				// BigDecimal

				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.writeObject(this.Longitude);

				// java.util.Date

				writeDate(this.DI_CreateDate, dos);

				// String

				writeString(this.DI_WorkFlowFileNme, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// java.util.Date

				writeDate(this.Inspection_Date, dos);

				// Integer

				writeInteger(this.Inspection_Score, dos);

				// Integer

				writeInteger(this.Street_Number, dos);

				// String

				writeString(this.Street_Name, dos);

				// String

				writeString(this.Street_Direction, dos);

				// String

				writeString(this.Street_Type, dos);

				// String

				writeString(this.Street_Unit, dos);

				// String

				writeString(this.Street_Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// Double

				if (this.Violation_Points___1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___1);
				}

				// Double

				if (this.Violation_Points___2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___2);
				}

				// Double

				if (this.Violation_Points___3 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___3);
				}

				// Double

				if (this.Violation_Points___4 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___4);
				}

				// Double

				if (this.Violation_Points___5 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___5);
				}

				// Double

				if (this.Violation_Points___6 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___6);
				}

				// Double

				if (this.Violation_Points___7 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___7);
				}

				// Double

				if (this.Violation_Points___8 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___8);
				}

				// Double

				if (this.Violation_Points___9 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___9);
				}

				// Double

				if (this.Violation_Points___10 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___10);
				}

				// Double

				if (this.Violation_Points___11 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___11);
				}

				// Double

				if (this.Violation_Points___12 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___12);
				}

				// Double

				if (this.Violation_Points___13 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___13);
				}

				// Double

				if (this.Violation_Points___14 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___14);
				}

				// Double

				if (this.Violation_Points___15 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___15);
				}

				// Double

				if (this.Violation_Points___16 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___16);
				}

				// Double

				if (this.Violation_Points___17 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___17);
				}

				// Double

				if (this.Violation_Points___18 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___18);
				}

				// Double

				if (this.Violation_Points___19 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___19);
				}

				// Double

				if (this.Violation_Points___20 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___20);
				}

				// Double

				if (this.Violation_Points___21 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___21);
				}

				// Double

				if (this.Violation_Points___22 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___22);
				}

				// Double

				if (this.Violation_Points___23 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___23);
				}

				// Double

				if (this.Violation_Points___24 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___24);
				}

				// Double

				if (this.Violation_Points___25 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___25);
				}

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.Address, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Longitude);

				// java.util.Date

				writeDate(this.DI_CreateDate, dos);

				// String

				writeString(this.DI_WorkFlowFileNme, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Inspection_ID=" + Inspection_ID);
			sb.append(",Restaurant_Name=" + Restaurant_Name);
			sb.append(",Inspection_Type=" + Inspection_Type);
			sb.append(",Inspection_Date=" + String.valueOf(Inspection_Date));
			sb.append(",Inspection_Score=" + String.valueOf(Inspection_Score));
			sb.append(",Street_Number=" + String.valueOf(Street_Number));
			sb.append(",Street_Name=" + Street_Name);
			sb.append(",Street_Direction=" + Street_Direction);
			sb.append(",Street_Type=" + Street_Type);
			sb.append(",Street_Unit=" + Street_Unit);
			sb.append(",Street_Address=" + Street_Address);
			sb.append(",Zip_Code=" + Zip_Code);
			sb.append(",Violation_Points___1=" + String.valueOf(Violation_Points___1));
			sb.append(",Violation_Points___2=" + String.valueOf(Violation_Points___2));
			sb.append(",Violation_Points___3=" + String.valueOf(Violation_Points___3));
			sb.append(",Violation_Points___4=" + String.valueOf(Violation_Points___4));
			sb.append(",Violation_Points___5=" + String.valueOf(Violation_Points___5));
			sb.append(",Violation_Points___6=" + String.valueOf(Violation_Points___6));
			sb.append(",Violation_Points___7=" + String.valueOf(Violation_Points___7));
			sb.append(",Violation_Points___8=" + String.valueOf(Violation_Points___8));
			sb.append(",Violation_Points___9=" + String.valueOf(Violation_Points___9));
			sb.append(",Violation_Points___10=" + String.valueOf(Violation_Points___10));
			sb.append(",Violation_Points___11=" + String.valueOf(Violation_Points___11));
			sb.append(",Violation_Points___12=" + String.valueOf(Violation_Points___12));
			sb.append(",Violation_Points___13=" + String.valueOf(Violation_Points___13));
			sb.append(",Violation_Points___14=" + String.valueOf(Violation_Points___14));
			sb.append(",Violation_Points___15=" + String.valueOf(Violation_Points___15));
			sb.append(",Violation_Points___16=" + String.valueOf(Violation_Points___16));
			sb.append(",Violation_Points___17=" + String.valueOf(Violation_Points___17));
			sb.append(",Violation_Points___18=" + String.valueOf(Violation_Points___18));
			sb.append(",Violation_Points___19=" + String.valueOf(Violation_Points___19));
			sb.append(",Violation_Points___20=" + String.valueOf(Violation_Points___20));
			sb.append(",Violation_Points___21=" + String.valueOf(Violation_Points___21));
			sb.append(",Violation_Points___22=" + String.valueOf(Violation_Points___22));
			sb.append(",Violation_Points___23=" + String.valueOf(Violation_Points___23));
			sb.append(",Violation_Points___24=" + String.valueOf(Violation_Points___24));
			sb.append(",Violation_Points___25=" + String.valueOf(Violation_Points___25));
			sb.append(",Inspection_Month=" + Inspection_Month);
			sb.append(",Inspection_Year=" + Inspection_Year);
			sb.append(",Address=" + Address);
			sb.append(",Latitude=" + String.valueOf(Latitude));
			sb.append(",Longitude=" + String.valueOf(Longitude));
			sb.append(",DI_CreateDate=" + String.valueOf(DI_CreateDate));
			sb.append(",DI_WorkFlowFileNme=" + DI_WorkFlowFileNme);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Inspection_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_ID);
			}

			sb.append("|");

			if (Restaurant_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Restaurant_Name);
			}

			sb.append("|");

			if (Inspection_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Type);
			}

			sb.append("|");

			if (Inspection_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Date);
			}

			sb.append("|");

			if (Inspection_Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Score);
			}

			sb.append("|");

			if (Street_Number == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Number);
			}

			sb.append("|");

			if (Street_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Name);
			}

			sb.append("|");

			if (Street_Direction == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Direction);
			}

			sb.append("|");

			if (Street_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Type);
			}

			sb.append("|");

			if (Street_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Unit);
			}

			sb.append("|");

			if (Street_Address == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Address);
			}

			sb.append("|");

			if (Zip_Code == null) {
				sb.append("<null>");
			} else {
				sb.append(Zip_Code);
			}

			sb.append("|");

			if (Violation_Points___1 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___1);
			}

			sb.append("|");

			if (Violation_Points___2 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___2);
			}

			sb.append("|");

			if (Violation_Points___3 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___3);
			}

			sb.append("|");

			if (Violation_Points___4 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___4);
			}

			sb.append("|");

			if (Violation_Points___5 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___5);
			}

			sb.append("|");

			if (Violation_Points___6 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___6);
			}

			sb.append("|");

			if (Violation_Points___7 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___7);
			}

			sb.append("|");

			if (Violation_Points___8 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___8);
			}

			sb.append("|");

			if (Violation_Points___9 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___9);
			}

			sb.append("|");

			if (Violation_Points___10 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___10);
			}

			sb.append("|");

			if (Violation_Points___11 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___11);
			}

			sb.append("|");

			if (Violation_Points___12 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___12);
			}

			sb.append("|");

			if (Violation_Points___13 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___13);
			}

			sb.append("|");

			if (Violation_Points___14 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___14);
			}

			sb.append("|");

			if (Violation_Points___15 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___15);
			}

			sb.append("|");

			if (Violation_Points___16 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___16);
			}

			sb.append("|");

			if (Violation_Points___17 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___17);
			}

			sb.append("|");

			if (Violation_Points___18 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___18);
			}

			sb.append("|");

			if (Violation_Points___19 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___19);
			}

			sb.append("|");

			if (Violation_Points___20 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___20);
			}

			sb.append("|");

			if (Violation_Points___21 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___21);
			}

			sb.append("|");

			if (Violation_Points___22 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___22);
			}

			sb.append("|");

			if (Violation_Points___23 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___23);
			}

			sb.append("|");

			if (Violation_Points___24 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___24);
			}

			sb.append("|");

			if (Violation_Points___25 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___25);
			}

			sb.append("|");

			if (Inspection_Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Month);
			}

			sb.append("|");

			if (Inspection_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Year);
			}

			sb.append("|");

			if (Address == null) {
				sb.append("<null>");
			} else {
				sb.append(Address);
			}

			sb.append("|");

			if (Latitude == null) {
				sb.append("<null>");
			} else {
				sb.append(Latitude);
			}

			sb.append("|");

			if (Longitude == null) {
				sb.append("<null>");
			} else {
				sb.append(Longitude);
			}

			sb.append("|");

			if (DI_CreateDate == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_CreateDate);
			}

			sb.append("|");

			if (DI_WorkFlowFileNme == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_WorkFlowFileNme);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(load_stg_dallasStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_stg_chicago = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_stg_chicago = new byte[0];

		public String Inspection_ID;

		public String getInspection_ID() {
			return this.Inspection_ID;
		}

		public Boolean Inspection_IDIsNullable() {
			return true;
		}

		public Boolean Inspection_IDIsKey() {
			return false;
		}

		public Integer Inspection_IDLength() {
			return 10;
		}

		public Integer Inspection_IDPrecision() {
			return 0;
		}

		public String Inspection_IDDefault() {

			return null;

		}

		public String Inspection_IDComment() {

			return "";

		}

		public String Inspection_IDPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_IDOriginalDbColumnName() {

			return "Inspection_ID";

		}

		public String Restaurant_Name;

		public String getRestaurant_Name() {
			return this.Restaurant_Name;
		}

		public Boolean Restaurant_NameIsNullable() {
			return true;
		}

		public Boolean Restaurant_NameIsKey() {
			return false;
		}

		public Integer Restaurant_NameLength() {
			return 40;
		}

		public Integer Restaurant_NamePrecision() {
			return 0;
		}

		public String Restaurant_NameDefault() {

			return null;

		}

		public String Restaurant_NameComment() {

			return "";

		}

		public String Restaurant_NamePattern() {

			return "dd-MM-yyyy";

		}

		public String Restaurant_NameOriginalDbColumnName() {

			return "Restaurant_Name";

		}

		public String Inspection_Type;

		public String getInspection_Type() {
			return this.Inspection_Type;
		}

		public Boolean Inspection_TypeIsNullable() {
			return true;
		}

		public Boolean Inspection_TypeIsKey() {
			return false;
		}

		public Integer Inspection_TypeLength() {
			return 7;
		}

		public Integer Inspection_TypePrecision() {
			return 0;
		}

		public String Inspection_TypeDefault() {

			return null;

		}

		public String Inspection_TypeComment() {

			return "";

		}

		public String Inspection_TypePattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_TypeOriginalDbColumnName() {

			return "Inspection_Type";

		}

		public String Inspection_Date;

		public String getInspection_Date() {
			return this.Inspection_Date;
		}

		public Boolean Inspection_DateIsNullable() {
			return true;
		}

		public Boolean Inspection_DateIsKey() {
			return false;
		}

		public Integer Inspection_DateLength() {
			return 300;
		}

		public Integer Inspection_DatePrecision() {
			return 0;
		}

		public String Inspection_DateDefault() {

			return null;

		}

		public String Inspection_DateComment() {

			return "";

		}

		public String Inspection_DatePattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_DateOriginalDbColumnName() {

			return "Inspection_Date";

		}

		public Integer Inspection_Score;

		public Integer getInspection_Score() {
			return this.Inspection_Score;
		}

		public Boolean Inspection_ScoreIsNullable() {
			return true;
		}

		public Boolean Inspection_ScoreIsKey() {
			return false;
		}

		public Integer Inspection_ScoreLength() {
			return 3;
		}

		public Integer Inspection_ScorePrecision() {
			return 0;
		}

		public String Inspection_ScoreDefault() {

			return null;

		}

		public String Inspection_ScoreComment() {

			return "";

		}

		public String Inspection_ScorePattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_ScoreOriginalDbColumnName() {

			return "Inspection_Score";

		}

		public Integer Street_Number;

		public Integer getStreet_Number() {
			return this.Street_Number;
		}

		public Boolean Street_NumberIsNullable() {
			return true;
		}

		public Boolean Street_NumberIsKey() {
			return false;
		}

		public Integer Street_NumberLength() {
			return 5;
		}

		public Integer Street_NumberPrecision() {
			return 0;
		}

		public String Street_NumberDefault() {

			return null;

		}

		public String Street_NumberComment() {

			return "";

		}

		public String Street_NumberPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_NumberOriginalDbColumnName() {

			return "Street_Number";

		}

		public String Street_Name;

		public String getStreet_Name() {
			return this.Street_Name;
		}

		public Boolean Street_NameIsNullable() {
			return true;
		}

		public Boolean Street_NameIsKey() {
			return false;
		}

		public Integer Street_NameLength() {
			return 16;
		}

		public Integer Street_NamePrecision() {
			return 0;
		}

		public String Street_NameDefault() {

			return null;

		}

		public String Street_NameComment() {

			return "";

		}

		public String Street_NamePattern() {

			return "dd-MM-yyyy";

		}

		public String Street_NameOriginalDbColumnName() {

			return "Street_Name";

		}

		public String Street_Direction;

		public String getStreet_Direction() {
			return this.Street_Direction;
		}

		public Boolean Street_DirectionIsNullable() {
			return true;
		}

		public Boolean Street_DirectionIsKey() {
			return false;
		}

		public Integer Street_DirectionLength() {
			return 1;
		}

		public Integer Street_DirectionPrecision() {
			return 0;
		}

		public String Street_DirectionDefault() {

			return null;

		}

		public String Street_DirectionComment() {

			return "";

		}

		public String Street_DirectionPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_DirectionOriginalDbColumnName() {

			return "Street_Direction";

		}

		public String Street_Type;

		public String getStreet_Type() {
			return this.Street_Type;
		}

		public Boolean Street_TypeIsNullable() {
			return true;
		}

		public Boolean Street_TypeIsKey() {
			return false;
		}

		public Integer Street_TypeLength() {
			return 4;
		}

		public Integer Street_TypePrecision() {
			return 0;
		}

		public String Street_TypeDefault() {

			return null;

		}

		public String Street_TypeComment() {

			return "";

		}

		public String Street_TypePattern() {

			return "dd-MM-yyyy";

		}

		public String Street_TypeOriginalDbColumnName() {

			return "Street_Type";

		}

		public String Street_Unit;

		public String getStreet_Unit() {
			return this.Street_Unit;
		}

		public Boolean Street_UnitIsNullable() {
			return true;
		}

		public Boolean Street_UnitIsKey() {
			return false;
		}

		public Integer Street_UnitLength() {
			return 5;
		}

		public Integer Street_UnitPrecision() {
			return 0;
		}

		public String Street_UnitDefault() {

			return null;

		}

		public String Street_UnitComment() {

			return "";

		}

		public String Street_UnitPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_UnitOriginalDbColumnName() {

			return "Street_Unit";

		}

		public String Street_Address;

		public String getStreet_Address() {
			return this.Street_Address;
		}

		public Boolean Street_AddressIsNullable() {
			return true;
		}

		public Boolean Street_AddressIsKey() {
			return false;
		}

		public Integer Street_AddressLength() {
			return 29;
		}

		public Integer Street_AddressPrecision() {
			return 0;
		}

		public String Street_AddressDefault() {

			return null;

		}

		public String Street_AddressComment() {

			return "";

		}

		public String Street_AddressPattern() {

			return "dd-MM-yyyy";

		}

		public String Street_AddressOriginalDbColumnName() {

			return "Street_Address";

		}

		public String Zip_Code;

		public String getZip_Code() {
			return this.Zip_Code;
		}

		public Boolean Zip_CodeIsNullable() {
			return true;
		}

		public Boolean Zip_CodeIsKey() {
			return false;
		}

		public Integer Zip_CodeLength() {
			return 5;
		}

		public Integer Zip_CodePrecision() {
			return 0;
		}

		public String Zip_CodeDefault() {

			return null;

		}

		public String Zip_CodeComment() {

			return "";

		}

		public String Zip_CodePattern() {

			return "dd-MM-yyyy";

		}

		public String Zip_CodeOriginalDbColumnName() {

			return "Zip_Code";

		}

		public String Violation_Description___1;

		public String getViolation_Description___1() {
			return this.Violation_Description___1;
		}

		public Boolean Violation_Description___1IsNullable() {
			return true;
		}

		public Boolean Violation_Description___1IsKey() {
			return false;
		}

		public Integer Violation_Description___1Length() {
			return 101;
		}

		public Integer Violation_Description___1Precision() {
			return 0;
		}

		public String Violation_Description___1Default() {

			return null;

		}

		public String Violation_Description___1Comment() {

			return "";

		}

		public String Violation_Description___1Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___1OriginalDbColumnName() {

			return "Violation_Description___1";

		}

		public Double Violation_Points___1;

		public Double getViolation_Points___1() {
			return this.Violation_Points___1;
		}

		public Boolean Violation_Points___1IsNullable() {
			return true;
		}

		public Boolean Violation_Points___1IsKey() {
			return false;
		}

		public Integer Violation_Points___1Length() {
			return 3;
		}

		public Integer Violation_Points___1Precision() {
			return 0;
		}

		public String Violation_Points___1Default() {

			return null;

		}

		public String Violation_Points___1Comment() {

			return "";

		}

		public String Violation_Points___1Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___1OriginalDbColumnName() {

			return "Violation_Points___1";

		}

		public String Violation_Detail___1;

		public String getViolation_Detail___1() {
			return this.Violation_Detail___1;
		}

		public Boolean Violation_Detail___1IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___1IsKey() {
			return false;
		}

		public Integer Violation_Detail___1Length() {
			return 271;
		}

		public Integer Violation_Detail___1Precision() {
			return 0;
		}

		public String Violation_Detail___1Default() {

			return null;

		}

		public String Violation_Detail___1Comment() {

			return "";

		}

		public String Violation_Detail___1Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___1OriginalDbColumnName() {

			return "Violation_Detail___1";

		}

		public String Violation_Memo___1;

		public String getViolation_Memo___1() {
			return this.Violation_Memo___1;
		}

		public Boolean Violation_Memo___1IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___1IsKey() {
			return false;
		}

		public Integer Violation_Memo___1Length() {
			return 236;
		}

		public Integer Violation_Memo___1Precision() {
			return 0;
		}

		public String Violation_Memo___1Default() {

			return null;

		}

		public String Violation_Memo___1Comment() {

			return "";

		}

		public String Violation_Memo___1Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___1OriginalDbColumnName() {

			return "Violation_Memo___1";

		}

		public String Violation_Description___2;

		public String getViolation_Description___2() {
			return this.Violation_Description___2;
		}

		public Boolean Violation_Description___2IsNullable() {
			return true;
		}

		public Boolean Violation_Description___2IsKey() {
			return false;
		}

		public Integer Violation_Description___2Length() {
			return 273;
		}

		public Integer Violation_Description___2Precision() {
			return 0;
		}

		public String Violation_Description___2Default() {

			return null;

		}

		public String Violation_Description___2Comment() {

			return "";

		}

		public String Violation_Description___2Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___2OriginalDbColumnName() {

			return "Violation_Description___2";

		}

		public Double Violation_Points___2;

		public Double getViolation_Points___2() {
			return this.Violation_Points___2;
		}

		public Boolean Violation_Points___2IsNullable() {
			return true;
		}

		public Boolean Violation_Points___2IsKey() {
			return false;
		}

		public Integer Violation_Points___2Length() {
			return 3;
		}

		public Integer Violation_Points___2Precision() {
			return 0;
		}

		public String Violation_Points___2Default() {

			return null;

		}

		public String Violation_Points___2Comment() {

			return "";

		}

		public String Violation_Points___2Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___2OriginalDbColumnName() {

			return "Violation_Points___2";

		}

		public String Violation_Detail___2;

		public String getViolation_Detail___2() {
			return this.Violation_Detail___2;
		}

		public Boolean Violation_Detail___2IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___2IsKey() {
			return false;
		}

		public Integer Violation_Detail___2Length() {
			return 156;
		}

		public Integer Violation_Detail___2Precision() {
			return 0;
		}

		public String Violation_Detail___2Default() {

			return null;

		}

		public String Violation_Detail___2Comment() {

			return "";

		}

		public String Violation_Detail___2Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___2OriginalDbColumnName() {

			return "Violation_Detail___2";

		}

		public String Violation_Memo___2;

		public String getViolation_Memo___2() {
			return this.Violation_Memo___2;
		}

		public Boolean Violation_Memo___2IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___2IsKey() {
			return false;
		}

		public Integer Violation_Memo___2Length() {
			return 188;
		}

		public Integer Violation_Memo___2Precision() {
			return 0;
		}

		public String Violation_Memo___2Default() {

			return null;

		}

		public String Violation_Memo___2Comment() {

			return "";

		}

		public String Violation_Memo___2Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___2OriginalDbColumnName() {

			return "Violation_Memo___2";

		}

		public String Violation_Description___3;

		public String getViolation_Description___3() {
			return this.Violation_Description___3;
		}

		public Boolean Violation_Description___3IsNullable() {
			return true;
		}

		public Boolean Violation_Description___3IsKey() {
			return false;
		}

		public Integer Violation_Description___3Length() {
			return 375;
		}

		public Integer Violation_Description___3Precision() {
			return 0;
		}

		public String Violation_Description___3Default() {

			return null;

		}

		public String Violation_Description___3Comment() {

			return "";

		}

		public String Violation_Description___3Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___3OriginalDbColumnName() {

			return "Violation_Description___3";

		}

		public Double Violation_Points___3;

		public Double getViolation_Points___3() {
			return this.Violation_Points___3;
		}

		public Boolean Violation_Points___3IsNullable() {
			return true;
		}

		public Boolean Violation_Points___3IsKey() {
			return false;
		}

		public Integer Violation_Points___3Length() {
			return 3;
		}

		public Integer Violation_Points___3Precision() {
			return 0;
		}

		public String Violation_Points___3Default() {

			return null;

		}

		public String Violation_Points___3Comment() {

			return "";

		}

		public String Violation_Points___3Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___3OriginalDbColumnName() {

			return "Violation_Points___3";

		}

		public String Violation_Detail___3;

		public String getViolation_Detail___3() {
			return this.Violation_Detail___3;
		}

		public Boolean Violation_Detail___3IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___3IsKey() {
			return false;
		}

		public Integer Violation_Detail___3Length() {
			return 236;
		}

		public Integer Violation_Detail___3Precision() {
			return 0;
		}

		public String Violation_Detail___3Default() {

			return null;

		}

		public String Violation_Detail___3Comment() {

			return "";

		}

		public String Violation_Detail___3Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___3OriginalDbColumnName() {

			return "Violation_Detail___3";

		}

		public String Violation_Memo___3;

		public String getViolation_Memo___3() {
			return this.Violation_Memo___3;
		}

		public Boolean Violation_Memo___3IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___3IsKey() {
			return false;
		}

		public Integer Violation_Memo___3Length() {
			return 136;
		}

		public Integer Violation_Memo___3Precision() {
			return 0;
		}

		public String Violation_Memo___3Default() {

			return null;

		}

		public String Violation_Memo___3Comment() {

			return "";

		}

		public String Violation_Memo___3Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___3OriginalDbColumnName() {

			return "Violation_Memo___3";

		}

		public String Violation_Description___4;

		public String getViolation_Description___4() {
			return this.Violation_Description___4;
		}

		public Boolean Violation_Description___4IsNullable() {
			return true;
		}

		public Boolean Violation_Description___4IsKey() {
			return false;
		}

		public Integer Violation_Description___4Length() {
			return 212;
		}

		public Integer Violation_Description___4Precision() {
			return 0;
		}

		public String Violation_Description___4Default() {

			return null;

		}

		public String Violation_Description___4Comment() {

			return "";

		}

		public String Violation_Description___4Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___4OriginalDbColumnName() {

			return "Violation_Description___4";

		}

		public Double Violation_Points___4;

		public Double getViolation_Points___4() {
			return this.Violation_Points___4;
		}

		public Boolean Violation_Points___4IsNullable() {
			return true;
		}

		public Boolean Violation_Points___4IsKey() {
			return false;
		}

		public Integer Violation_Points___4Length() {
			return 3;
		}

		public Integer Violation_Points___4Precision() {
			return 0;
		}

		public String Violation_Points___4Default() {

			return null;

		}

		public String Violation_Points___4Comment() {

			return "";

		}

		public String Violation_Points___4Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___4OriginalDbColumnName() {

			return "Violation_Points___4";

		}

		public String Violation_Detail___4;

		public String getViolation_Detail___4() {
			return this.Violation_Detail___4;
		}

		public Boolean Violation_Detail___4IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___4IsKey() {
			return false;
		}

		public Integer Violation_Detail___4Length() {
			return 266;
		}

		public Integer Violation_Detail___4Precision() {
			return 0;
		}

		public String Violation_Detail___4Default() {

			return null;

		}

		public String Violation_Detail___4Comment() {

			return "";

		}

		public String Violation_Detail___4Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___4OriginalDbColumnName() {

			return "Violation_Detail___4";

		}

		public String Violation_Memo___4;

		public String getViolation_Memo___4() {
			return this.Violation_Memo___4;
		}

		public Boolean Violation_Memo___4IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___4IsKey() {
			return false;
		}

		public Integer Violation_Memo___4Length() {
			return 248;
		}

		public Integer Violation_Memo___4Precision() {
			return 0;
		}

		public String Violation_Memo___4Default() {

			return null;

		}

		public String Violation_Memo___4Comment() {

			return "";

		}

		public String Violation_Memo___4Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___4OriginalDbColumnName() {

			return "Violation_Memo___4";

		}

		public String Violation_Description___5;

		public String getViolation_Description___5() {
			return this.Violation_Description___5;
		}

		public Boolean Violation_Description___5IsNullable() {
			return true;
		}

		public Boolean Violation_Description___5IsKey() {
			return false;
		}

		public Integer Violation_Description___5Length() {
			return 307;
		}

		public Integer Violation_Description___5Precision() {
			return 0;
		}

		public String Violation_Description___5Default() {

			return null;

		}

		public String Violation_Description___5Comment() {

			return "";

		}

		public String Violation_Description___5Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___5OriginalDbColumnName() {

			return "Violation_Description___5";

		}

		public Double Violation_Points___5;

		public Double getViolation_Points___5() {
			return this.Violation_Points___5;
		}

		public Boolean Violation_Points___5IsNullable() {
			return true;
		}

		public Boolean Violation_Points___5IsKey() {
			return false;
		}

		public Integer Violation_Points___5Length() {
			return 3;
		}

		public Integer Violation_Points___5Precision() {
			return 0;
		}

		public String Violation_Points___5Default() {

			return null;

		}

		public String Violation_Points___5Comment() {

			return "";

		}

		public String Violation_Points___5Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___5OriginalDbColumnName() {

			return "Violation_Points___5";

		}

		public String Violation_Detail___5;

		public String getViolation_Detail___5() {
			return this.Violation_Detail___5;
		}

		public Boolean Violation_Detail___5IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___5IsKey() {
			return false;
		}

		public Integer Violation_Detail___5Length() {
			return 161;
		}

		public Integer Violation_Detail___5Precision() {
			return 0;
		}

		public String Violation_Detail___5Default() {

			return null;

		}

		public String Violation_Detail___5Comment() {

			return "";

		}

		public String Violation_Detail___5Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___5OriginalDbColumnName() {

			return "Violation_Detail___5";

		}

		public String Violation_Memo___5;

		public String getViolation_Memo___5() {
			return this.Violation_Memo___5;
		}

		public Boolean Violation_Memo___5IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___5IsKey() {
			return false;
		}

		public Integer Violation_Memo___5Length() {
			return 292;
		}

		public Integer Violation_Memo___5Precision() {
			return 0;
		}

		public String Violation_Memo___5Default() {

			return null;

		}

		public String Violation_Memo___5Comment() {

			return "";

		}

		public String Violation_Memo___5Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___5OriginalDbColumnName() {

			return "Violation_Memo___5";

		}

		public String Violation_Description___6;

		public String getViolation_Description___6() {
			return this.Violation_Description___6;
		}

		public Boolean Violation_Description___6IsNullable() {
			return true;
		}

		public Boolean Violation_Description___6IsKey() {
			return false;
		}

		public Integer Violation_Description___6Length() {
			return 68;
		}

		public Integer Violation_Description___6Precision() {
			return 0;
		}

		public String Violation_Description___6Default() {

			return null;

		}

		public String Violation_Description___6Comment() {

			return "";

		}

		public String Violation_Description___6Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___6OriginalDbColumnName() {

			return "Violation_Description___6";

		}

		public Double Violation_Points___6;

		public Double getViolation_Points___6() {
			return this.Violation_Points___6;
		}

		public Boolean Violation_Points___6IsNullable() {
			return true;
		}

		public Boolean Violation_Points___6IsKey() {
			return false;
		}

		public Integer Violation_Points___6Length() {
			return 3;
		}

		public Integer Violation_Points___6Precision() {
			return 0;
		}

		public String Violation_Points___6Default() {

			return null;

		}

		public String Violation_Points___6Comment() {

			return "";

		}

		public String Violation_Points___6Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___6OriginalDbColumnName() {

			return "Violation_Points___6";

		}

		public String Violation_Detail___6;

		public String getViolation_Detail___6() {
			return this.Violation_Detail___6;
		}

		public Boolean Violation_Detail___6IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___6IsKey() {
			return false;
		}

		public Integer Violation_Detail___6Length() {
			return 79;
		}

		public Integer Violation_Detail___6Precision() {
			return 0;
		}

		public String Violation_Detail___6Default() {

			return null;

		}

		public String Violation_Detail___6Comment() {

			return "";

		}

		public String Violation_Detail___6Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___6OriginalDbColumnName() {

			return "Violation_Detail___6";

		}

		public String Violation_Memo___6;

		public String getViolation_Memo___6() {
			return this.Violation_Memo___6;
		}

		public Boolean Violation_Memo___6IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___6IsKey() {
			return false;
		}

		public Integer Violation_Memo___6Length() {
			return 110;
		}

		public Integer Violation_Memo___6Precision() {
			return 0;
		}

		public String Violation_Memo___6Default() {

			return null;

		}

		public String Violation_Memo___6Comment() {

			return "";

		}

		public String Violation_Memo___6Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___6OriginalDbColumnName() {

			return "Violation_Memo___6";

		}

		public String Violation_Description___7;

		public String getViolation_Description___7() {
			return this.Violation_Description___7;
		}

		public Boolean Violation_Description___7IsNullable() {
			return true;
		}

		public Boolean Violation_Description___7IsKey() {
			return false;
		}

		public Integer Violation_Description___7Length() {
			return 250;
		}

		public Integer Violation_Description___7Precision() {
			return 0;
		}

		public String Violation_Description___7Default() {

			return null;

		}

		public String Violation_Description___7Comment() {

			return "";

		}

		public String Violation_Description___7Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___7OriginalDbColumnName() {

			return "Violation_Description___7";

		}

		public Double Violation_Points___7;

		public Double getViolation_Points___7() {
			return this.Violation_Points___7;
		}

		public Boolean Violation_Points___7IsNullable() {
			return true;
		}

		public Boolean Violation_Points___7IsKey() {
			return false;
		}

		public Integer Violation_Points___7Length() {
			return 3;
		}

		public Integer Violation_Points___7Precision() {
			return 0;
		}

		public String Violation_Points___7Default() {

			return null;

		}

		public String Violation_Points___7Comment() {

			return "";

		}

		public String Violation_Points___7Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___7OriginalDbColumnName() {

			return "Violation_Points___7";

		}

		public String Violation_Detail___7;

		public String getViolation_Detail___7() {
			return this.Violation_Detail___7;
		}

		public Boolean Violation_Detail___7IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___7IsKey() {
			return false;
		}

		public Integer Violation_Detail___7Length() {
			return 43;
		}

		public Integer Violation_Detail___7Precision() {
			return 0;
		}

		public String Violation_Detail___7Default() {

			return null;

		}

		public String Violation_Detail___7Comment() {

			return "";

		}

		public String Violation_Detail___7Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___7OriginalDbColumnName() {

			return "Violation_Detail___7";

		}

		public String Violation_Memo___7;

		public String getViolation_Memo___7() {
			return this.Violation_Memo___7;
		}

		public Boolean Violation_Memo___7IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___7IsKey() {
			return false;
		}

		public Integer Violation_Memo___7Length() {
			return 117;
		}

		public Integer Violation_Memo___7Precision() {
			return 0;
		}

		public String Violation_Memo___7Default() {

			return null;

		}

		public String Violation_Memo___7Comment() {

			return "";

		}

		public String Violation_Memo___7Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___7OriginalDbColumnName() {

			return "Violation_Memo___7";

		}

		public String Violation_Description___8;

		public String getViolation_Description___8() {
			return this.Violation_Description___8;
		}

		public Boolean Violation_Description___8IsNullable() {
			return true;
		}

		public Boolean Violation_Description___8IsKey() {
			return false;
		}

		public Integer Violation_Description___8Length() {
			return 191;
		}

		public Integer Violation_Description___8Precision() {
			return 0;
		}

		public String Violation_Description___8Default() {

			return null;

		}

		public String Violation_Description___8Comment() {

			return "";

		}

		public String Violation_Description___8Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___8OriginalDbColumnName() {

			return "Violation_Description___8";

		}

		public Double Violation_Points___8;

		public Double getViolation_Points___8() {
			return this.Violation_Points___8;
		}

		public Boolean Violation_Points___8IsNullable() {
			return true;
		}

		public Boolean Violation_Points___8IsKey() {
			return false;
		}

		public Integer Violation_Points___8Length() {
			return 3;
		}

		public Integer Violation_Points___8Precision() {
			return 0;
		}

		public String Violation_Points___8Default() {

			return null;

		}

		public String Violation_Points___8Comment() {

			return "";

		}

		public String Violation_Points___8Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___8OriginalDbColumnName() {

			return "Violation_Points___8";

		}

		public String Violation_Detail___8;

		public String getViolation_Detail___8() {
			return this.Violation_Detail___8;
		}

		public Boolean Violation_Detail___8IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___8IsKey() {
			return false;
		}

		public Integer Violation_Detail___8Length() {
			return 27;
		}

		public Integer Violation_Detail___8Precision() {
			return 0;
		}

		public String Violation_Detail___8Default() {

			return null;

		}

		public String Violation_Detail___8Comment() {

			return "";

		}

		public String Violation_Detail___8Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___8OriginalDbColumnName() {

			return "Violation_Detail___8";

		}

		public String Violation_Memo___8;

		public String getViolation_Memo___8() {
			return this.Violation_Memo___8;
		}

		public Boolean Violation_Memo___8IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___8IsKey() {
			return false;
		}

		public Integer Violation_Memo___8Length() {
			return 101;
		}

		public Integer Violation_Memo___8Precision() {
			return 0;
		}

		public String Violation_Memo___8Default() {

			return null;

		}

		public String Violation_Memo___8Comment() {

			return "";

		}

		public String Violation_Memo___8Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___8OriginalDbColumnName() {

			return "Violation_Memo___8";

		}

		public String Violation_Description___9;

		public String getViolation_Description___9() {
			return this.Violation_Description___9;
		}

		public Boolean Violation_Description___9IsNullable() {
			return true;
		}

		public Boolean Violation_Description___9IsKey() {
			return false;
		}

		public Integer Violation_Description___9Length() {
			return 205;
		}

		public Integer Violation_Description___9Precision() {
			return 0;
		}

		public String Violation_Description___9Default() {

			return null;

		}

		public String Violation_Description___9Comment() {

			return "";

		}

		public String Violation_Description___9Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___9OriginalDbColumnName() {

			return "Violation_Description___9";

		}

		public Double Violation_Points___9;

		public Double getViolation_Points___9() {
			return this.Violation_Points___9;
		}

		public Boolean Violation_Points___9IsNullable() {
			return true;
		}

		public Boolean Violation_Points___9IsKey() {
			return false;
		}

		public Integer Violation_Points___9Length() {
			return 3;
		}

		public Integer Violation_Points___9Precision() {
			return 0;
		}

		public String Violation_Points___9Default() {

			return null;

		}

		public String Violation_Points___9Comment() {

			return "";

		}

		public String Violation_Points___9Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___9OriginalDbColumnName() {

			return "Violation_Points___9";

		}

		public String Violation_Detail___9;

		public String getViolation_Detail___9() {
			return this.Violation_Detail___9;
		}

		public Boolean Violation_Detail___9IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___9IsKey() {
			return false;
		}

		public Integer Violation_Detail___9Length() {
			return 51;
		}

		public Integer Violation_Detail___9Precision() {
			return 0;
		}

		public String Violation_Detail___9Default() {

			return null;

		}

		public String Violation_Detail___9Comment() {

			return "";

		}

		public String Violation_Detail___9Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___9OriginalDbColumnName() {

			return "Violation_Detail___9";

		}

		public String Violation_Memo___9;

		public String getViolation_Memo___9() {
			return this.Violation_Memo___9;
		}

		public Boolean Violation_Memo___9IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___9IsKey() {
			return false;
		}

		public Integer Violation_Memo___9Length() {
			return 235;
		}

		public Integer Violation_Memo___9Precision() {
			return 0;
		}

		public String Violation_Memo___9Default() {

			return null;

		}

		public String Violation_Memo___9Comment() {

			return "";

		}

		public String Violation_Memo___9Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___9OriginalDbColumnName() {

			return "Violation_Memo___9";

		}

		public String Violation_Description___10;

		public String getViolation_Description___10() {
			return this.Violation_Description___10;
		}

		public Boolean Violation_Description___10IsNullable() {
			return true;
		}

		public Boolean Violation_Description___10IsKey() {
			return false;
		}

		public Integer Violation_Description___10Length() {
			return 110;
		}

		public Integer Violation_Description___10Precision() {
			return 0;
		}

		public String Violation_Description___10Default() {

			return null;

		}

		public String Violation_Description___10Comment() {

			return "";

		}

		public String Violation_Description___10Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___10OriginalDbColumnName() {

			return "Violation_Description___10";

		}

		public Double Violation_Points___10;

		public Double getViolation_Points___10() {
			return this.Violation_Points___10;
		}

		public Boolean Violation_Points___10IsNullable() {
			return true;
		}

		public Boolean Violation_Points___10IsKey() {
			return false;
		}

		public Integer Violation_Points___10Length() {
			return 3;
		}

		public Integer Violation_Points___10Precision() {
			return 0;
		}

		public String Violation_Points___10Default() {

			return null;

		}

		public String Violation_Points___10Comment() {

			return "";

		}

		public String Violation_Points___10Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___10OriginalDbColumnName() {

			return "Violation_Points___10";

		}

		public String Violation_Detail___10;

		public String getViolation_Detail___10() {
			return this.Violation_Detail___10;
		}

		public Boolean Violation_Detail___10IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___10IsKey() {
			return false;
		}

		public Integer Violation_Detail___10Length() {
			return 70;
		}

		public Integer Violation_Detail___10Precision() {
			return 0;
		}

		public String Violation_Detail___10Default() {

			return null;

		}

		public String Violation_Detail___10Comment() {

			return "";

		}

		public String Violation_Detail___10Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___10OriginalDbColumnName() {

			return "Violation_Detail___10";

		}

		public String Violation_Memo___10;

		public String getViolation_Memo___10() {
			return this.Violation_Memo___10;
		}

		public Boolean Violation_Memo___10IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___10IsKey() {
			return false;
		}

		public Integer Violation_Memo___10Length() {
			return 97;
		}

		public Integer Violation_Memo___10Precision() {
			return 0;
		}

		public String Violation_Memo___10Default() {

			return null;

		}

		public String Violation_Memo___10Comment() {

			return "";

		}

		public String Violation_Memo___10Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___10OriginalDbColumnName() {

			return "Violation_Memo___10";

		}

		public String Violation_Description___11;

		public String getViolation_Description___11() {
			return this.Violation_Description___11;
		}

		public Boolean Violation_Description___11IsNullable() {
			return true;
		}

		public Boolean Violation_Description___11IsKey() {
			return false;
		}

		public Integer Violation_Description___11Length() {
			return 141;
		}

		public Integer Violation_Description___11Precision() {
			return 0;
		}

		public String Violation_Description___11Default() {

			return null;

		}

		public String Violation_Description___11Comment() {

			return "";

		}

		public String Violation_Description___11Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___11OriginalDbColumnName() {

			return "Violation_Description___11";

		}

		public Double Violation_Points___11;

		public Double getViolation_Points___11() {
			return this.Violation_Points___11;
		}

		public Boolean Violation_Points___11IsNullable() {
			return true;
		}

		public Boolean Violation_Points___11IsKey() {
			return false;
		}

		public Integer Violation_Points___11Length() {
			return 3;
		}

		public Integer Violation_Points___11Precision() {
			return 0;
		}

		public String Violation_Points___11Default() {

			return null;

		}

		public String Violation_Points___11Comment() {

			return "";

		}

		public String Violation_Points___11Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___11OriginalDbColumnName() {

			return "Violation_Points___11";

		}

		public String Violation_Detail___11;

		public String getViolation_Detail___11() {
			return this.Violation_Detail___11;
		}

		public Boolean Violation_Detail___11IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___11IsKey() {
			return false;
		}

		public Integer Violation_Detail___11Length() {
			return 243;
		}

		public Integer Violation_Detail___11Precision() {
			return 0;
		}

		public String Violation_Detail___11Default() {

			return null;

		}

		public String Violation_Detail___11Comment() {

			return "";

		}

		public String Violation_Detail___11Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___11OriginalDbColumnName() {

			return "Violation_Detail___11";

		}

		public String Violation_Memo___11;

		public String getViolation_Memo___11() {
			return this.Violation_Memo___11;
		}

		public Boolean Violation_Memo___11IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___11IsKey() {
			return false;
		}

		public Integer Violation_Memo___11Length() {
			return 132;
		}

		public Integer Violation_Memo___11Precision() {
			return 0;
		}

		public String Violation_Memo___11Default() {

			return null;

		}

		public String Violation_Memo___11Comment() {

			return "";

		}

		public String Violation_Memo___11Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___11OriginalDbColumnName() {

			return "Violation_Memo___11";

		}

		public String Violation_Description___12;

		public String getViolation_Description___12() {
			return this.Violation_Description___12;
		}

		public Boolean Violation_Description___12IsNullable() {
			return true;
		}

		public Boolean Violation_Description___12IsKey() {
			return false;
		}

		public Integer Violation_Description___12Length() {
			return 169;
		}

		public Integer Violation_Description___12Precision() {
			return 0;
		}

		public String Violation_Description___12Default() {

			return null;

		}

		public String Violation_Description___12Comment() {

			return "";

		}

		public String Violation_Description___12Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___12OriginalDbColumnName() {

			return "Violation_Description___12";

		}

		public Double Violation_Points___12;

		public Double getViolation_Points___12() {
			return this.Violation_Points___12;
		}

		public Boolean Violation_Points___12IsNullable() {
			return true;
		}

		public Boolean Violation_Points___12IsKey() {
			return false;
		}

		public Integer Violation_Points___12Length() {
			return 3;
		}

		public Integer Violation_Points___12Precision() {
			return 0;
		}

		public String Violation_Points___12Default() {

			return null;

		}

		public String Violation_Points___12Comment() {

			return "";

		}

		public String Violation_Points___12Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___12OriginalDbColumnName() {

			return "Violation_Points___12";

		}

		public String Violation_Detail___12;

		public String getViolation_Detail___12() {
			return this.Violation_Detail___12;
		}

		public Boolean Violation_Detail___12IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___12IsKey() {
			return false;
		}

		public Integer Violation_Detail___12Length() {
			return 23;
		}

		public Integer Violation_Detail___12Precision() {
			return 0;
		}

		public String Violation_Detail___12Default() {

			return null;

		}

		public String Violation_Detail___12Comment() {

			return "";

		}

		public String Violation_Detail___12Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___12OriginalDbColumnName() {

			return "Violation_Detail___12";

		}

		public String Violation_Memo___12;

		public String getViolation_Memo___12() {
			return this.Violation_Memo___12;
		}

		public Boolean Violation_Memo___12IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___12IsKey() {
			return false;
		}

		public Integer Violation_Memo___12Length() {
			return 29;
		}

		public Integer Violation_Memo___12Precision() {
			return 0;
		}

		public String Violation_Memo___12Default() {

			return null;

		}

		public String Violation_Memo___12Comment() {

			return "";

		}

		public String Violation_Memo___12Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___12OriginalDbColumnName() {

			return "Violation_Memo___12";

		}

		public String Violation_Description___13;

		public String getViolation_Description___13() {
			return this.Violation_Description___13;
		}

		public Boolean Violation_Description___13IsNullable() {
			return true;
		}

		public Boolean Violation_Description___13IsKey() {
			return false;
		}

		public Integer Violation_Description___13Length() {
			return 27;
		}

		public Integer Violation_Description___13Precision() {
			return 0;
		}

		public String Violation_Description___13Default() {

			return null;

		}

		public String Violation_Description___13Comment() {

			return "";

		}

		public String Violation_Description___13Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___13OriginalDbColumnName() {

			return "Violation_Description___13";

		}

		public Double Violation_Points___13;

		public Double getViolation_Points___13() {
			return this.Violation_Points___13;
		}

		public Boolean Violation_Points___13IsNullable() {
			return true;
		}

		public Boolean Violation_Points___13IsKey() {
			return false;
		}

		public Integer Violation_Points___13Length() {
			return 3;
		}

		public Integer Violation_Points___13Precision() {
			return 0;
		}

		public String Violation_Points___13Default() {

			return null;

		}

		public String Violation_Points___13Comment() {

			return "";

		}

		public String Violation_Points___13Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___13OriginalDbColumnName() {

			return "Violation_Points___13";

		}

		public String Violation_Detail___13;

		public String getViolation_Detail___13() {
			return this.Violation_Detail___13;
		}

		public Boolean Violation_Detail___13IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___13IsKey() {
			return false;
		}

		public Integer Violation_Detail___13Length() {
			return 55;
		}

		public Integer Violation_Detail___13Precision() {
			return 0;
		}

		public String Violation_Detail___13Default() {

			return null;

		}

		public String Violation_Detail___13Comment() {

			return "";

		}

		public String Violation_Detail___13Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___13OriginalDbColumnName() {

			return "Violation_Detail___13";

		}

		public String Violation_Memo___13;

		public String getViolation_Memo___13() {
			return this.Violation_Memo___13;
		}

		public Boolean Violation_Memo___13IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___13IsKey() {
			return false;
		}

		public Integer Violation_Memo___13Length() {
			return 143;
		}

		public Integer Violation_Memo___13Precision() {
			return 0;
		}

		public String Violation_Memo___13Default() {

			return null;

		}

		public String Violation_Memo___13Comment() {

			return "";

		}

		public String Violation_Memo___13Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___13OriginalDbColumnName() {

			return "Violation_Memo___13";

		}

		public String Violation_Description___14;

		public String getViolation_Description___14() {
			return this.Violation_Description___14;
		}

		public Boolean Violation_Description___14IsNullable() {
			return true;
		}

		public Boolean Violation_Description___14IsKey() {
			return false;
		}

		public Integer Violation_Description___14Length() {
			return 17;
		}

		public Integer Violation_Description___14Precision() {
			return 0;
		}

		public String Violation_Description___14Default() {

			return null;

		}

		public String Violation_Description___14Comment() {

			return "";

		}

		public String Violation_Description___14Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___14OriginalDbColumnName() {

			return "Violation_Description___14";

		}

		public Double Violation_Points___14;

		public Double getViolation_Points___14() {
			return this.Violation_Points___14;
		}

		public Boolean Violation_Points___14IsNullable() {
			return true;
		}

		public Boolean Violation_Points___14IsKey() {
			return false;
		}

		public Integer Violation_Points___14Length() {
			return 3;
		}

		public Integer Violation_Points___14Precision() {
			return 0;
		}

		public String Violation_Points___14Default() {

			return null;

		}

		public String Violation_Points___14Comment() {

			return "";

		}

		public String Violation_Points___14Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___14OriginalDbColumnName() {

			return "Violation_Points___14";

		}

		public String Violation_Detail___14;

		public String getViolation_Detail___14() {
			return this.Violation_Detail___14;
		}

		public Boolean Violation_Detail___14IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___14IsKey() {
			return false;
		}

		public Integer Violation_Detail___14Length() {
			return 104;
		}

		public Integer Violation_Detail___14Precision() {
			return 0;
		}

		public String Violation_Detail___14Default() {

			return null;

		}

		public String Violation_Detail___14Comment() {

			return "";

		}

		public String Violation_Detail___14Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___14OriginalDbColumnName() {

			return "Violation_Detail___14";

		}

		public String Violation_Memo___14;

		public String getViolation_Memo___14() {
			return this.Violation_Memo___14;
		}

		public Boolean Violation_Memo___14IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___14IsKey() {
			return false;
		}

		public Integer Violation_Memo___14Length() {
			return 276;
		}

		public Integer Violation_Memo___14Precision() {
			return 0;
		}

		public String Violation_Memo___14Default() {

			return null;

		}

		public String Violation_Memo___14Comment() {

			return "";

		}

		public String Violation_Memo___14Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___14OriginalDbColumnName() {

			return "Violation_Memo___14";

		}

		public String Violation_Description___15;

		public String getViolation_Description___15() {
			return this.Violation_Description___15;
		}

		public Boolean Violation_Description___15IsNullable() {
			return true;
		}

		public Boolean Violation_Description___15IsKey() {
			return false;
		}

		public Integer Violation_Description___15Length() {
			return 67;
		}

		public Integer Violation_Description___15Precision() {
			return 0;
		}

		public String Violation_Description___15Default() {

			return null;

		}

		public String Violation_Description___15Comment() {

			return "";

		}

		public String Violation_Description___15Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___15OriginalDbColumnName() {

			return "Violation_Description___15";

		}

		public Double Violation_Points___15;

		public Double getViolation_Points___15() {
			return this.Violation_Points___15;
		}

		public Boolean Violation_Points___15IsNullable() {
			return true;
		}

		public Boolean Violation_Points___15IsKey() {
			return false;
		}

		public Integer Violation_Points___15Length() {
			return 3;
		}

		public Integer Violation_Points___15Precision() {
			return 0;
		}

		public String Violation_Points___15Default() {

			return null;

		}

		public String Violation_Points___15Comment() {

			return "";

		}

		public String Violation_Points___15Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___15OriginalDbColumnName() {

			return "Violation_Points___15";

		}

		public String Violation_Detail___15;

		public String getViolation_Detail___15() {
			return this.Violation_Detail___15;
		}

		public Boolean Violation_Detail___15IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___15IsKey() {
			return false;
		}

		public Integer Violation_Detail___15Length() {
			return 47;
		}

		public Integer Violation_Detail___15Precision() {
			return 0;
		}

		public String Violation_Detail___15Default() {

			return null;

		}

		public String Violation_Detail___15Comment() {

			return "";

		}

		public String Violation_Detail___15Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___15OriginalDbColumnName() {

			return "Violation_Detail___15";

		}

		public String Violation_Memo___15;

		public String getViolation_Memo___15() {
			return this.Violation_Memo___15;
		}

		public Boolean Violation_Memo___15IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___15IsKey() {
			return false;
		}

		public Integer Violation_Memo___15Length() {
			return 8;
		}

		public Integer Violation_Memo___15Precision() {
			return 0;
		}

		public String Violation_Memo___15Default() {

			return null;

		}

		public String Violation_Memo___15Comment() {

			return "";

		}

		public String Violation_Memo___15Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___15OriginalDbColumnName() {

			return "Violation_Memo___15";

		}

		public String Violation_Description___16;

		public String getViolation_Description___16() {
			return this.Violation_Description___16;
		}

		public Boolean Violation_Description___16IsNullable() {
			return true;
		}

		public Boolean Violation_Description___16IsKey() {
			return false;
		}

		public Integer Violation_Description___16Length() {
			return 45;
		}

		public Integer Violation_Description___16Precision() {
			return 0;
		}

		public String Violation_Description___16Default() {

			return null;

		}

		public String Violation_Description___16Comment() {

			return "";

		}

		public String Violation_Description___16Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___16OriginalDbColumnName() {

			return "Violation_Description___16";

		}

		public Double Violation_Points___16;

		public Double getViolation_Points___16() {
			return this.Violation_Points___16;
		}

		public Boolean Violation_Points___16IsNullable() {
			return true;
		}

		public Boolean Violation_Points___16IsKey() {
			return false;
		}

		public Integer Violation_Points___16Length() {
			return 3;
		}

		public Integer Violation_Points___16Precision() {
			return 0;
		}

		public String Violation_Points___16Default() {

			return null;

		}

		public String Violation_Points___16Comment() {

			return "";

		}

		public String Violation_Points___16Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___16OriginalDbColumnName() {

			return "Violation_Points___16";

		}

		public String Violation_Detail___16;

		public String getViolation_Detail___16() {
			return this.Violation_Detail___16;
		}

		public Boolean Violation_Detail___16IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___16IsKey() {
			return false;
		}

		public Integer Violation_Detail___16Length() {
			return 48;
		}

		public Integer Violation_Detail___16Precision() {
			return 0;
		}

		public String Violation_Detail___16Default() {

			return null;

		}

		public String Violation_Detail___16Comment() {

			return "";

		}

		public String Violation_Detail___16Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___16OriginalDbColumnName() {

			return "Violation_Detail___16";

		}

		public String Violation_Memo___16;

		public String getViolation_Memo___16() {
			return this.Violation_Memo___16;
		}

		public Boolean Violation_Memo___16IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___16IsKey() {
			return false;
		}

		public Integer Violation_Memo___16Length() {
			return 10;
		}

		public Integer Violation_Memo___16Precision() {
			return 0;
		}

		public String Violation_Memo___16Default() {

			return null;

		}

		public String Violation_Memo___16Comment() {

			return "";

		}

		public String Violation_Memo___16Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___16OriginalDbColumnName() {

			return "Violation_Memo___16";

		}

		public String Violation_Description___17;

		public String getViolation_Description___17() {
			return this.Violation_Description___17;
		}

		public Boolean Violation_Description___17IsNullable() {
			return true;
		}

		public Boolean Violation_Description___17IsKey() {
			return false;
		}

		public Integer Violation_Description___17Length() {
			return 8;
		}

		public Integer Violation_Description___17Precision() {
			return 0;
		}

		public String Violation_Description___17Default() {

			return null;

		}

		public String Violation_Description___17Comment() {

			return "";

		}

		public String Violation_Description___17Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___17OriginalDbColumnName() {

			return "Violation_Description___17";

		}

		public Double Violation_Points___17;

		public Double getViolation_Points___17() {
			return this.Violation_Points___17;
		}

		public Boolean Violation_Points___17IsNullable() {
			return true;
		}

		public Boolean Violation_Points___17IsKey() {
			return false;
		}

		public Integer Violation_Points___17Length() {
			return 3;
		}

		public Integer Violation_Points___17Precision() {
			return 0;
		}

		public String Violation_Points___17Default() {

			return null;

		}

		public String Violation_Points___17Comment() {

			return "";

		}

		public String Violation_Points___17Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___17OriginalDbColumnName() {

			return "Violation_Points___17";

		}

		public String Violation_Detail___17;

		public String getViolation_Detail___17() {
			return this.Violation_Detail___17;
		}

		public Boolean Violation_Detail___17IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___17IsKey() {
			return false;
		}

		public Integer Violation_Detail___17Length() {
			return 9;
		}

		public Integer Violation_Detail___17Precision() {
			return 0;
		}

		public String Violation_Detail___17Default() {

			return null;

		}

		public String Violation_Detail___17Comment() {

			return "";

		}

		public String Violation_Detail___17Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___17OriginalDbColumnName() {

			return "Violation_Detail___17";

		}

		public String Violation_Memo___17;

		public String getViolation_Memo___17() {
			return this.Violation_Memo___17;
		}

		public Boolean Violation_Memo___17IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___17IsKey() {
			return false;
		}

		public Integer Violation_Memo___17Length() {
			return 10;
		}

		public Integer Violation_Memo___17Precision() {
			return 0;
		}

		public String Violation_Memo___17Default() {

			return null;

		}

		public String Violation_Memo___17Comment() {

			return "";

		}

		public String Violation_Memo___17Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___17OriginalDbColumnName() {

			return "Violation_Memo___17";

		}

		public String Violation_Description___18;

		public String getViolation_Description___18() {
			return this.Violation_Description___18;
		}

		public Boolean Violation_Description___18IsNullable() {
			return true;
		}

		public Boolean Violation_Description___18IsKey() {
			return false;
		}

		public Integer Violation_Description___18Length() {
			return 8;
		}

		public Integer Violation_Description___18Precision() {
			return 0;
		}

		public String Violation_Description___18Default() {

			return null;

		}

		public String Violation_Description___18Comment() {

			return "";

		}

		public String Violation_Description___18Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___18OriginalDbColumnName() {

			return "Violation_Description___18";

		}

		public Double Violation_Points___18;

		public Double getViolation_Points___18() {
			return this.Violation_Points___18;
		}

		public Boolean Violation_Points___18IsNullable() {
			return true;
		}

		public Boolean Violation_Points___18IsKey() {
			return false;
		}

		public Integer Violation_Points___18Length() {
			return 3;
		}

		public Integer Violation_Points___18Precision() {
			return 0;
		}

		public String Violation_Points___18Default() {

			return null;

		}

		public String Violation_Points___18Comment() {

			return "";

		}

		public String Violation_Points___18Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___18OriginalDbColumnName() {

			return "Violation_Points___18";

		}

		public String Violation_Detail___18;

		public String getViolation_Detail___18() {
			return this.Violation_Detail___18;
		}

		public Boolean Violation_Detail___18IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___18IsKey() {
			return false;
		}

		public Integer Violation_Detail___18Length() {
			return 8;
		}

		public Integer Violation_Detail___18Precision() {
			return 0;
		}

		public String Violation_Detail___18Default() {

			return null;

		}

		public String Violation_Detail___18Comment() {

			return "";

		}

		public String Violation_Detail___18Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___18OriginalDbColumnName() {

			return "Violation_Detail___18";

		}

		public String Violation_Memo___18;

		public String getViolation_Memo___18() {
			return this.Violation_Memo___18;
		}

		public Boolean Violation_Memo___18IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___18IsKey() {
			return false;
		}

		public Integer Violation_Memo___18Length() {
			return 115;
		}

		public Integer Violation_Memo___18Precision() {
			return 0;
		}

		public String Violation_Memo___18Default() {

			return null;

		}

		public String Violation_Memo___18Comment() {

			return "";

		}

		public String Violation_Memo___18Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___18OriginalDbColumnName() {

			return "Violation_Memo___18";

		}

		public String Violation_Description___19;

		public String getViolation_Description___19() {
			return this.Violation_Description___19;
		}

		public Boolean Violation_Description___19IsNullable() {
			return true;
		}

		public Boolean Violation_Description___19IsKey() {
			return false;
		}

		public Integer Violation_Description___19Length() {
			return 19;
		}

		public Integer Violation_Description___19Precision() {
			return 0;
		}

		public String Violation_Description___19Default() {

			return null;

		}

		public String Violation_Description___19Comment() {

			return "";

		}

		public String Violation_Description___19Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___19OriginalDbColumnName() {

			return "Violation_Description___19";

		}

		public Double Violation_Points___19;

		public Double getViolation_Points___19() {
			return this.Violation_Points___19;
		}

		public Boolean Violation_Points___19IsNullable() {
			return true;
		}

		public Boolean Violation_Points___19IsKey() {
			return false;
		}

		public Integer Violation_Points___19Length() {
			return 3;
		}

		public Integer Violation_Points___19Precision() {
			return 0;
		}

		public String Violation_Points___19Default() {

			return null;

		}

		public String Violation_Points___19Comment() {

			return "";

		}

		public String Violation_Points___19Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___19OriginalDbColumnName() {

			return "Violation_Points___19";

		}

		public String Violation_Detail___19;

		public String getViolation_Detail___19() {
			return this.Violation_Detail___19;
		}

		public Boolean Violation_Detail___19IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___19IsKey() {
			return false;
		}

		public Integer Violation_Detail___19Length() {
			return 31;
		}

		public Integer Violation_Detail___19Precision() {
			return 0;
		}

		public String Violation_Detail___19Default() {

			return null;

		}

		public String Violation_Detail___19Comment() {

			return "";

		}

		public String Violation_Detail___19Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___19OriginalDbColumnName() {

			return "Violation_Detail___19";

		}

		public String Violation_Memo___19;

		public String getViolation_Memo___19() {
			return this.Violation_Memo___19;
		}

		public Boolean Violation_Memo___19IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___19IsKey() {
			return false;
		}

		public Integer Violation_Memo___19Length() {
			return 41;
		}

		public Integer Violation_Memo___19Precision() {
			return 0;
		}

		public String Violation_Memo___19Default() {

			return null;

		}

		public String Violation_Memo___19Comment() {

			return "";

		}

		public String Violation_Memo___19Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___19OriginalDbColumnName() {

			return "Violation_Memo___19";

		}

		public String Violation_Description___20;

		public String getViolation_Description___20() {
			return this.Violation_Description___20;
		}

		public Boolean Violation_Description___20IsNullable() {
			return true;
		}

		public Boolean Violation_Description___20IsKey() {
			return false;
		}

		public Integer Violation_Description___20Length() {
			return 3;
		}

		public Integer Violation_Description___20Precision() {
			return 0;
		}

		public String Violation_Description___20Default() {

			return null;

		}

		public String Violation_Description___20Comment() {

			return "";

		}

		public String Violation_Description___20Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___20OriginalDbColumnName() {

			return "Violation_Description___20";

		}

		public Double Violation_Points___20;

		public Double getViolation_Points___20() {
			return this.Violation_Points___20;
		}

		public Boolean Violation_Points___20IsNullable() {
			return true;
		}

		public Boolean Violation_Points___20IsKey() {
			return false;
		}

		public Integer Violation_Points___20Length() {
			return 3;
		}

		public Integer Violation_Points___20Precision() {
			return 0;
		}

		public String Violation_Points___20Default() {

			return null;

		}

		public String Violation_Points___20Comment() {

			return "";

		}

		public String Violation_Points___20Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___20OriginalDbColumnName() {

			return "Violation_Points___20";

		}

		public String Violation_Detail___20;

		public String getViolation_Detail___20() {
			return this.Violation_Detail___20;
		}

		public Boolean Violation_Detail___20IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___20IsKey() {
			return false;
		}

		public Integer Violation_Detail___20Length() {
			return 29;
		}

		public Integer Violation_Detail___20Precision() {
			return 0;
		}

		public String Violation_Detail___20Default() {

			return null;

		}

		public String Violation_Detail___20Comment() {

			return "";

		}

		public String Violation_Detail___20Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___20OriginalDbColumnName() {

			return "Violation_Detail___20";

		}

		public String Violation__Memo___20;

		public String getViolation__Memo___20() {
			return this.Violation__Memo___20;
		}

		public Boolean Violation__Memo___20IsNullable() {
			return true;
		}

		public Boolean Violation__Memo___20IsKey() {
			return false;
		}

		public Integer Violation__Memo___20Length() {
			return 133;
		}

		public Integer Violation__Memo___20Precision() {
			return 0;
		}

		public String Violation__Memo___20Default() {

			return null;

		}

		public String Violation__Memo___20Comment() {

			return "";

		}

		public String Violation__Memo___20Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation__Memo___20OriginalDbColumnName() {

			return "Violation__Memo___20";

		}

		public String Violation_Description___21;

		public String getViolation_Description___21() {
			return this.Violation_Description___21;
		}

		public Boolean Violation_Description___21IsNullable() {
			return true;
		}

		public Boolean Violation_Description___21IsKey() {
			return false;
		}

		public Integer Violation_Description___21Length() {
			return 20;
		}

		public Integer Violation_Description___21Precision() {
			return 0;
		}

		public String Violation_Description___21Default() {

			return null;

		}

		public String Violation_Description___21Comment() {

			return "";

		}

		public String Violation_Description___21Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___21OriginalDbColumnName() {

			return "Violation_Description___21";

		}

		public Double Violation_Points___21;

		public Double getViolation_Points___21() {
			return this.Violation_Points___21;
		}

		public Boolean Violation_Points___21IsNullable() {
			return true;
		}

		public Boolean Violation_Points___21IsKey() {
			return false;
		}

		public Integer Violation_Points___21Length() {
			return 3;
		}

		public Integer Violation_Points___21Precision() {
			return 0;
		}

		public String Violation_Points___21Default() {

			return null;

		}

		public String Violation_Points___21Comment() {

			return "";

		}

		public String Violation_Points___21Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___21OriginalDbColumnName() {

			return "Violation_Points___21";

		}

		public String Violation_Detail___21;

		public String getViolation_Detail___21() {
			return this.Violation_Detail___21;
		}

		public Boolean Violation_Detail___21IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___21IsKey() {
			return false;
		}

		public Integer Violation_Detail___21Length() {
			return 3;
		}

		public Integer Violation_Detail___21Precision() {
			return 0;
		}

		public String Violation_Detail___21Default() {

			return null;

		}

		public String Violation_Detail___21Comment() {

			return "";

		}

		public String Violation_Detail___21Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___21OriginalDbColumnName() {

			return "Violation_Detail___21";

		}

		public String Violation_Memo___21;

		public String getViolation_Memo___21() {
			return this.Violation_Memo___21;
		}

		public Boolean Violation_Memo___21IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___21IsKey() {
			return false;
		}

		public Integer Violation_Memo___21Length() {
			return 110;
		}

		public Integer Violation_Memo___21Precision() {
			return 0;
		}

		public String Violation_Memo___21Default() {

			return null;

		}

		public String Violation_Memo___21Comment() {

			return "";

		}

		public String Violation_Memo___21Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___21OriginalDbColumnName() {

			return "Violation_Memo___21";

		}

		public String Violation_Description___22;

		public String getViolation_Description___22() {
			return this.Violation_Description___22;
		}

		public Boolean Violation_Description___22IsNullable() {
			return true;
		}

		public Boolean Violation_Description___22IsKey() {
			return false;
		}

		public Integer Violation_Description___22Length() {
			return 56;
		}

		public Integer Violation_Description___22Precision() {
			return 0;
		}

		public String Violation_Description___22Default() {

			return null;

		}

		public String Violation_Description___22Comment() {

			return "";

		}

		public String Violation_Description___22Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___22OriginalDbColumnName() {

			return "Violation_Description___22";

		}

		public Double Violation_Points___22;

		public Double getViolation_Points___22() {
			return this.Violation_Points___22;
		}

		public Boolean Violation_Points___22IsNullable() {
			return true;
		}

		public Boolean Violation_Points___22IsKey() {
			return false;
		}

		public Integer Violation_Points___22Length() {
			return 3;
		}

		public Integer Violation_Points___22Precision() {
			return 0;
		}

		public String Violation_Points___22Default() {

			return null;

		}

		public String Violation_Points___22Comment() {

			return "";

		}

		public String Violation_Points___22Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___22OriginalDbColumnName() {

			return "Violation_Points___22";

		}

		public String Violation_Detail___22;

		public String getViolation_Detail___22() {
			return this.Violation_Detail___22;
		}

		public Boolean Violation_Detail___22IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___22IsKey() {
			return false;
		}

		public Integer Violation_Detail___22Length() {
			return 11;
		}

		public Integer Violation_Detail___22Precision() {
			return 0;
		}

		public String Violation_Detail___22Default() {

			return null;

		}

		public String Violation_Detail___22Comment() {

			return "";

		}

		public String Violation_Detail___22Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___22OriginalDbColumnName() {

			return "Violation_Detail___22";

		}

		public String Violation_Memo___22;

		public String getViolation_Memo___22() {
			return this.Violation_Memo___22;
		}

		public Boolean Violation_Memo___22IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___22IsKey() {
			return false;
		}

		public Integer Violation_Memo___22Length() {
			return 97;
		}

		public Integer Violation_Memo___22Precision() {
			return 0;
		}

		public String Violation_Memo___22Default() {

			return null;

		}

		public String Violation_Memo___22Comment() {

			return "";

		}

		public String Violation_Memo___22Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___22OriginalDbColumnName() {

			return "Violation_Memo___22";

		}

		public String Violation_Description___23;

		public String getViolation_Description___23() {
			return this.Violation_Description___23;
		}

		public Boolean Violation_Description___23IsNullable() {
			return true;
		}

		public Boolean Violation_Description___23IsKey() {
			return false;
		}

		public Integer Violation_Description___23Length() {
			return 69;
		}

		public Integer Violation_Description___23Precision() {
			return 0;
		}

		public String Violation_Description___23Default() {

			return null;

		}

		public String Violation_Description___23Comment() {

			return "";

		}

		public String Violation_Description___23Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___23OriginalDbColumnName() {

			return "Violation_Description___23";

		}

		public Double Violation_Points___23;

		public Double getViolation_Points___23() {
			return this.Violation_Points___23;
		}

		public Boolean Violation_Points___23IsNullable() {
			return true;
		}

		public Boolean Violation_Points___23IsKey() {
			return false;
		}

		public Integer Violation_Points___23Length() {
			return 3;
		}

		public Integer Violation_Points___23Precision() {
			return 0;
		}

		public String Violation_Points___23Default() {

			return null;

		}

		public String Violation_Points___23Comment() {

			return "";

		}

		public String Violation_Points___23Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___23OriginalDbColumnName() {

			return "Violation_Points___23";

		}

		public String Violation_Detail___23;

		public String getViolation_Detail___23() {
			return this.Violation_Detail___23;
		}

		public Boolean Violation_Detail___23IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___23IsKey() {
			return false;
		}

		public Integer Violation_Detail___23Length() {
			return 26;
		}

		public Integer Violation_Detail___23Precision() {
			return 0;
		}

		public String Violation_Detail___23Default() {

			return null;

		}

		public String Violation_Detail___23Comment() {

			return "";

		}

		public String Violation_Detail___23Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___23OriginalDbColumnName() {

			return "Violation_Detail___23";

		}

		public String Violation_Memo___23;

		public String getViolation_Memo___23() {
			return this.Violation_Memo___23;
		}

		public Boolean Violation_Memo___23IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___23IsKey() {
			return false;
		}

		public Integer Violation_Memo___23Length() {
			return 19;
		}

		public Integer Violation_Memo___23Precision() {
			return 0;
		}

		public String Violation_Memo___23Default() {

			return null;

		}

		public String Violation_Memo___23Comment() {

			return "";

		}

		public String Violation_Memo___23Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___23OriginalDbColumnName() {

			return "Violation_Memo___23";

		}

		public String Violation_Description___24;

		public String getViolation_Description___24() {
			return this.Violation_Description___24;
		}

		public Boolean Violation_Description___24IsNullable() {
			return true;
		}

		public Boolean Violation_Description___24IsKey() {
			return false;
		}

		public Integer Violation_Description___24Length() {
			return 22;
		}

		public Integer Violation_Description___24Precision() {
			return 0;
		}

		public String Violation_Description___24Default() {

			return null;

		}

		public String Violation_Description___24Comment() {

			return "";

		}

		public String Violation_Description___24Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___24OriginalDbColumnName() {

			return "Violation_Description___24";

		}

		public Double Violation_Points___24;

		public Double getViolation_Points___24() {
			return this.Violation_Points___24;
		}

		public Boolean Violation_Points___24IsNullable() {
			return true;
		}

		public Boolean Violation_Points___24IsKey() {
			return false;
		}

		public Integer Violation_Points___24Length() {
			return 3;
		}

		public Integer Violation_Points___24Precision() {
			return 0;
		}

		public String Violation_Points___24Default() {

			return null;

		}

		public String Violation_Points___24Comment() {

			return "";

		}

		public String Violation_Points___24Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___24OriginalDbColumnName() {

			return "Violation_Points___24";

		}

		public String Violation_Detail___24;

		public String getViolation_Detail___24() {
			return this.Violation_Detail___24;
		}

		public Boolean Violation_Detail___24IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___24IsKey() {
			return false;
		}

		public Integer Violation_Detail___24Length() {
			return 3;
		}

		public Integer Violation_Detail___24Precision() {
			return 0;
		}

		public String Violation_Detail___24Default() {

			return null;

		}

		public String Violation_Detail___24Comment() {

			return "";

		}

		public String Violation_Detail___24Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___24OriginalDbColumnName() {

			return "Violation_Detail___24";

		}

		public String Violation_Memo___24;

		public String getViolation_Memo___24() {
			return this.Violation_Memo___24;
		}

		public Boolean Violation_Memo___24IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___24IsKey() {
			return false;
		}

		public Integer Violation_Memo___24Length() {
			return 48;
		}

		public Integer Violation_Memo___24Precision() {
			return 0;
		}

		public String Violation_Memo___24Default() {

			return null;

		}

		public String Violation_Memo___24Comment() {

			return "";

		}

		public String Violation_Memo___24Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___24OriginalDbColumnName() {

			return "Violation_Memo___24";

		}

		public String Violation_Description___25;

		public String getViolation_Description___25() {
			return this.Violation_Description___25;
		}

		public Boolean Violation_Description___25IsNullable() {
			return true;
		}

		public Boolean Violation_Description___25IsKey() {
			return false;
		}

		public Integer Violation_Description___25Length() {
			return 175;
		}

		public Integer Violation_Description___25Precision() {
			return 0;
		}

		public String Violation_Description___25Default() {

			return null;

		}

		public String Violation_Description___25Comment() {

			return "";

		}

		public String Violation_Description___25Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Description___25OriginalDbColumnName() {

			return "Violation_Description___25";

		}

		public Double Violation_Points___25;

		public Double getViolation_Points___25() {
			return this.Violation_Points___25;
		}

		public Boolean Violation_Points___25IsNullable() {
			return true;
		}

		public Boolean Violation_Points___25IsKey() {
			return false;
		}

		public Integer Violation_Points___25Length() {
			return 3;
		}

		public Integer Violation_Points___25Precision() {
			return 0;
		}

		public String Violation_Points___25Default() {

			return null;

		}

		public String Violation_Points___25Comment() {

			return "";

		}

		public String Violation_Points___25Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Points___25OriginalDbColumnName() {

			return "Violation_Points___25";

		}

		public String Violation_Detail___25;

		public String getViolation_Detail___25() {
			return this.Violation_Detail___25;
		}

		public Boolean Violation_Detail___25IsNullable() {
			return true;
		}

		public Boolean Violation_Detail___25IsKey() {
			return false;
		}

		public Integer Violation_Detail___25Length() {
			return 0;
		}

		public Integer Violation_Detail___25Precision() {
			return 0;
		}

		public String Violation_Detail___25Default() {

			return null;

		}

		public String Violation_Detail___25Comment() {

			return "";

		}

		public String Violation_Detail___25Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Detail___25OriginalDbColumnName() {

			return "Violation_Detail___25";

		}

		public String Violation_Memo___25;

		public String getViolation_Memo___25() {
			return this.Violation_Memo___25;
		}

		public Boolean Violation_Memo___25IsNullable() {
			return true;
		}

		public Boolean Violation_Memo___25IsKey() {
			return false;
		}

		public Integer Violation_Memo___25Length() {
			return 0;
		}

		public Integer Violation_Memo___25Precision() {
			return 0;
		}

		public String Violation_Memo___25Default() {

			return null;

		}

		public String Violation_Memo___25Comment() {

			return "";

		}

		public String Violation_Memo___25Pattern() {

			return "dd-MM-yyyy";

		}

		public String Violation_Memo___25OriginalDbColumnName() {

			return "Violation_Memo___25";

		}

		public String Inspection_Month;

		public String getInspection_Month() {
			return this.Inspection_Month;
		}

		public Boolean Inspection_MonthIsNullable() {
			return true;
		}

		public Boolean Inspection_MonthIsKey() {
			return false;
		}

		public Integer Inspection_MonthLength() {
			return 6;
		}

		public Integer Inspection_MonthPrecision() {
			return 0;
		}

		public String Inspection_MonthDefault() {

			return null;

		}

		public String Inspection_MonthComment() {

			return "";

		}

		public String Inspection_MonthPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_MonthOriginalDbColumnName() {

			return "Inspection_Month";

		}

		public String Inspection_Year;

		public String getInspection_Year() {
			return this.Inspection_Year;
		}

		public Boolean Inspection_YearIsNullable() {
			return true;
		}

		public Boolean Inspection_YearIsKey() {
			return false;
		}

		public Integer Inspection_YearLength() {
			return 6;
		}

		public Integer Inspection_YearPrecision() {
			return 0;
		}

		public String Inspection_YearDefault() {

			return null;

		}

		public String Inspection_YearComment() {

			return "";

		}

		public String Inspection_YearPattern() {

			return "dd-MM-yyyy";

		}

		public String Inspection_YearOriginalDbColumnName() {

			return "Inspection_Year";

		}

		public String end;

		public String getEnd() {
			return this.end;
		}

		public Boolean endIsNullable() {
			return true;
		}

		public Boolean endIsKey() {
			return false;
		}

		public Integer endLength() {
			return 6;
		}

		public Integer endPrecision() {
			return 0;
		}

		public String endDefault() {

			return null;

		}

		public String endComment() {

			return "";

		}

		public String endPattern() {

			return "dd-MM-yyyy";

		}

		public String endOriginalDbColumnName() {

			return "end";

		}

		public String Address;

		public String getAddress() {
			return this.Address;
		}

		public Boolean AddressIsNullable() {
			return true;
		}

		public Boolean AddressIsKey() {
			return false;
		}

		public Integer AddressLength() {
			return 25;
		}

		public Integer AddressPrecision() {
			return 0;
		}

		public String AddressDefault() {

			return null;

		}

		public String AddressComment() {

			return "";

		}

		public String AddressPattern() {

			return "dd-MM-yyyy";

		}

		public String AddressOriginalDbColumnName() {

			return "Address";

		}

		public BigDecimal Latitude;

		public BigDecimal getLatitude() {
			return this.Latitude;
		}

		public Boolean LatitudeIsNullable() {
			return true;
		}

		public Boolean LatitudeIsKey() {
			return false;
		}

		public Integer LatitudeLength() {
			return 14;
		}

		public Integer LatitudePrecision() {
			return 0;
		}

		public String LatitudeDefault() {

			return null;

		}

		public String LatitudeComment() {

			return "";

		}

		public String LatitudePattern() {

			return "dd-MM-yyyy";

		}

		public String LatitudeOriginalDbColumnName() {

			return "Latitude";

		}

		public BigDecimal Longitude;

		public BigDecimal getLongitude() {
			return this.Longitude;
		}

		public Boolean LongitudeIsNullable() {
			return true;
		}

		public Boolean LongitudeIsKey() {
			return false;
		}

		public Integer LongitudeLength() {
			return 22;
		}

		public Integer LongitudePrecision() {
			return 0;
		}

		public String LongitudeDefault() {

			return null;

		}

		public String LongitudeComment() {

			return "";

		}

		public String LongitudePattern() {

			return "dd-MM-yyyy";

		}

		public String LongitudeOriginalDbColumnName() {

			return "Longitude";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_stg_chicago.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_stg_chicago.length == 0) {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_stg_chicago, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_stg_chicago, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_stg_chicago.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_stg_chicago.length == 0) {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_stg_chicago = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_stg_chicago, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_stg_chicago, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_stg_chicago) {

				try {

					int length = 0;

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.Inspection_Type = readString(dis);

					this.Inspection_Date = readString(dis);

					this.Inspection_Score = readInteger(dis);

					this.Street_Number = readInteger(dis);

					this.Street_Name = readString(dis);

					this.Street_Direction = readString(dis);

					this.Street_Type = readString(dis);

					this.Street_Unit = readString(dis);

					this.Street_Address = readString(dis);

					this.Zip_Code = readString(dis);

					this.Violation_Description___1 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___1 = null;
					} else {
						this.Violation_Points___1 = dis.readDouble();
					}

					this.Violation_Detail___1 = readString(dis);

					this.Violation_Memo___1 = readString(dis);

					this.Violation_Description___2 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___2 = null;
					} else {
						this.Violation_Points___2 = dis.readDouble();
					}

					this.Violation_Detail___2 = readString(dis);

					this.Violation_Memo___2 = readString(dis);

					this.Violation_Description___3 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___3 = null;
					} else {
						this.Violation_Points___3 = dis.readDouble();
					}

					this.Violation_Detail___3 = readString(dis);

					this.Violation_Memo___3 = readString(dis);

					this.Violation_Description___4 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___4 = null;
					} else {
						this.Violation_Points___4 = dis.readDouble();
					}

					this.Violation_Detail___4 = readString(dis);

					this.Violation_Memo___4 = readString(dis);

					this.Violation_Description___5 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___5 = null;
					} else {
						this.Violation_Points___5 = dis.readDouble();
					}

					this.Violation_Detail___5 = readString(dis);

					this.Violation_Memo___5 = readString(dis);

					this.Violation_Description___6 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___6 = null;
					} else {
						this.Violation_Points___6 = dis.readDouble();
					}

					this.Violation_Detail___6 = readString(dis);

					this.Violation_Memo___6 = readString(dis);

					this.Violation_Description___7 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___7 = null;
					} else {
						this.Violation_Points___7 = dis.readDouble();
					}

					this.Violation_Detail___7 = readString(dis);

					this.Violation_Memo___7 = readString(dis);

					this.Violation_Description___8 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___8 = null;
					} else {
						this.Violation_Points___8 = dis.readDouble();
					}

					this.Violation_Detail___8 = readString(dis);

					this.Violation_Memo___8 = readString(dis);

					this.Violation_Description___9 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___9 = null;
					} else {
						this.Violation_Points___9 = dis.readDouble();
					}

					this.Violation_Detail___9 = readString(dis);

					this.Violation_Memo___9 = readString(dis);

					this.Violation_Description___10 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___10 = null;
					} else {
						this.Violation_Points___10 = dis.readDouble();
					}

					this.Violation_Detail___10 = readString(dis);

					this.Violation_Memo___10 = readString(dis);

					this.Violation_Description___11 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___11 = null;
					} else {
						this.Violation_Points___11 = dis.readDouble();
					}

					this.Violation_Detail___11 = readString(dis);

					this.Violation_Memo___11 = readString(dis);

					this.Violation_Description___12 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___12 = null;
					} else {
						this.Violation_Points___12 = dis.readDouble();
					}

					this.Violation_Detail___12 = readString(dis);

					this.Violation_Memo___12 = readString(dis);

					this.Violation_Description___13 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___13 = null;
					} else {
						this.Violation_Points___13 = dis.readDouble();
					}

					this.Violation_Detail___13 = readString(dis);

					this.Violation_Memo___13 = readString(dis);

					this.Violation_Description___14 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___14 = null;
					} else {
						this.Violation_Points___14 = dis.readDouble();
					}

					this.Violation_Detail___14 = readString(dis);

					this.Violation_Memo___14 = readString(dis);

					this.Violation_Description___15 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___15 = null;
					} else {
						this.Violation_Points___15 = dis.readDouble();
					}

					this.Violation_Detail___15 = readString(dis);

					this.Violation_Memo___15 = readString(dis);

					this.Violation_Description___16 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___16 = null;
					} else {
						this.Violation_Points___16 = dis.readDouble();
					}

					this.Violation_Detail___16 = readString(dis);

					this.Violation_Memo___16 = readString(dis);

					this.Violation_Description___17 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___17 = null;
					} else {
						this.Violation_Points___17 = dis.readDouble();
					}

					this.Violation_Detail___17 = readString(dis);

					this.Violation_Memo___17 = readString(dis);

					this.Violation_Description___18 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___18 = null;
					} else {
						this.Violation_Points___18 = dis.readDouble();
					}

					this.Violation_Detail___18 = readString(dis);

					this.Violation_Memo___18 = readString(dis);

					this.Violation_Description___19 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___19 = null;
					} else {
						this.Violation_Points___19 = dis.readDouble();
					}

					this.Violation_Detail___19 = readString(dis);

					this.Violation_Memo___19 = readString(dis);

					this.Violation_Description___20 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___20 = null;
					} else {
						this.Violation_Points___20 = dis.readDouble();
					}

					this.Violation_Detail___20 = readString(dis);

					this.Violation__Memo___20 = readString(dis);

					this.Violation_Description___21 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___21 = null;
					} else {
						this.Violation_Points___21 = dis.readDouble();
					}

					this.Violation_Detail___21 = readString(dis);

					this.Violation_Memo___21 = readString(dis);

					this.Violation_Description___22 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___22 = null;
					} else {
						this.Violation_Points___22 = dis.readDouble();
					}

					this.Violation_Detail___22 = readString(dis);

					this.Violation_Memo___22 = readString(dis);

					this.Violation_Description___23 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___23 = null;
					} else {
						this.Violation_Points___23 = dis.readDouble();
					}

					this.Violation_Detail___23 = readString(dis);

					this.Violation_Memo___23 = readString(dis);

					this.Violation_Description___24 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___24 = null;
					} else {
						this.Violation_Points___24 = dis.readDouble();
					}

					this.Violation_Detail___24 = readString(dis);

					this.Violation_Memo___24 = readString(dis);

					this.Violation_Description___25 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___25 = null;
					} else {
						this.Violation_Points___25 = dis.readDouble();
					}

					this.Violation_Detail___25 = readString(dis);

					this.Violation_Memo___25 = readString(dis);

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.end = readString(dis);

					this.Address = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_stg_chicago) {

				try {

					int length = 0;

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.Inspection_Type = readString(dis);

					this.Inspection_Date = readString(dis);

					this.Inspection_Score = readInteger(dis);

					this.Street_Number = readInteger(dis);

					this.Street_Name = readString(dis);

					this.Street_Direction = readString(dis);

					this.Street_Type = readString(dis);

					this.Street_Unit = readString(dis);

					this.Street_Address = readString(dis);

					this.Zip_Code = readString(dis);

					this.Violation_Description___1 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___1 = null;
					} else {
						this.Violation_Points___1 = dis.readDouble();
					}

					this.Violation_Detail___1 = readString(dis);

					this.Violation_Memo___1 = readString(dis);

					this.Violation_Description___2 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___2 = null;
					} else {
						this.Violation_Points___2 = dis.readDouble();
					}

					this.Violation_Detail___2 = readString(dis);

					this.Violation_Memo___2 = readString(dis);

					this.Violation_Description___3 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___3 = null;
					} else {
						this.Violation_Points___3 = dis.readDouble();
					}

					this.Violation_Detail___3 = readString(dis);

					this.Violation_Memo___3 = readString(dis);

					this.Violation_Description___4 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___4 = null;
					} else {
						this.Violation_Points___4 = dis.readDouble();
					}

					this.Violation_Detail___4 = readString(dis);

					this.Violation_Memo___4 = readString(dis);

					this.Violation_Description___5 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___5 = null;
					} else {
						this.Violation_Points___5 = dis.readDouble();
					}

					this.Violation_Detail___5 = readString(dis);

					this.Violation_Memo___5 = readString(dis);

					this.Violation_Description___6 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___6 = null;
					} else {
						this.Violation_Points___6 = dis.readDouble();
					}

					this.Violation_Detail___6 = readString(dis);

					this.Violation_Memo___6 = readString(dis);

					this.Violation_Description___7 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___7 = null;
					} else {
						this.Violation_Points___7 = dis.readDouble();
					}

					this.Violation_Detail___7 = readString(dis);

					this.Violation_Memo___7 = readString(dis);

					this.Violation_Description___8 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___8 = null;
					} else {
						this.Violation_Points___8 = dis.readDouble();
					}

					this.Violation_Detail___8 = readString(dis);

					this.Violation_Memo___8 = readString(dis);

					this.Violation_Description___9 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___9 = null;
					} else {
						this.Violation_Points___9 = dis.readDouble();
					}

					this.Violation_Detail___9 = readString(dis);

					this.Violation_Memo___9 = readString(dis);

					this.Violation_Description___10 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___10 = null;
					} else {
						this.Violation_Points___10 = dis.readDouble();
					}

					this.Violation_Detail___10 = readString(dis);

					this.Violation_Memo___10 = readString(dis);

					this.Violation_Description___11 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___11 = null;
					} else {
						this.Violation_Points___11 = dis.readDouble();
					}

					this.Violation_Detail___11 = readString(dis);

					this.Violation_Memo___11 = readString(dis);

					this.Violation_Description___12 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___12 = null;
					} else {
						this.Violation_Points___12 = dis.readDouble();
					}

					this.Violation_Detail___12 = readString(dis);

					this.Violation_Memo___12 = readString(dis);

					this.Violation_Description___13 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___13 = null;
					} else {
						this.Violation_Points___13 = dis.readDouble();
					}

					this.Violation_Detail___13 = readString(dis);

					this.Violation_Memo___13 = readString(dis);

					this.Violation_Description___14 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___14 = null;
					} else {
						this.Violation_Points___14 = dis.readDouble();
					}

					this.Violation_Detail___14 = readString(dis);

					this.Violation_Memo___14 = readString(dis);

					this.Violation_Description___15 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___15 = null;
					} else {
						this.Violation_Points___15 = dis.readDouble();
					}

					this.Violation_Detail___15 = readString(dis);

					this.Violation_Memo___15 = readString(dis);

					this.Violation_Description___16 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___16 = null;
					} else {
						this.Violation_Points___16 = dis.readDouble();
					}

					this.Violation_Detail___16 = readString(dis);

					this.Violation_Memo___16 = readString(dis);

					this.Violation_Description___17 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___17 = null;
					} else {
						this.Violation_Points___17 = dis.readDouble();
					}

					this.Violation_Detail___17 = readString(dis);

					this.Violation_Memo___17 = readString(dis);

					this.Violation_Description___18 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___18 = null;
					} else {
						this.Violation_Points___18 = dis.readDouble();
					}

					this.Violation_Detail___18 = readString(dis);

					this.Violation_Memo___18 = readString(dis);

					this.Violation_Description___19 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___19 = null;
					} else {
						this.Violation_Points___19 = dis.readDouble();
					}

					this.Violation_Detail___19 = readString(dis);

					this.Violation_Memo___19 = readString(dis);

					this.Violation_Description___20 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___20 = null;
					} else {
						this.Violation_Points___20 = dis.readDouble();
					}

					this.Violation_Detail___20 = readString(dis);

					this.Violation__Memo___20 = readString(dis);

					this.Violation_Description___21 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___21 = null;
					} else {
						this.Violation_Points___21 = dis.readDouble();
					}

					this.Violation_Detail___21 = readString(dis);

					this.Violation_Memo___21 = readString(dis);

					this.Violation_Description___22 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___22 = null;
					} else {
						this.Violation_Points___22 = dis.readDouble();
					}

					this.Violation_Detail___22 = readString(dis);

					this.Violation_Memo___22 = readString(dis);

					this.Violation_Description___23 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___23 = null;
					} else {
						this.Violation_Points___23 = dis.readDouble();
					}

					this.Violation_Detail___23 = readString(dis);

					this.Violation_Memo___23 = readString(dis);

					this.Violation_Description___24 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___24 = null;
					} else {
						this.Violation_Points___24 = dis.readDouble();
					}

					this.Violation_Detail___24 = readString(dis);

					this.Violation_Memo___24 = readString(dis);

					this.Violation_Description___25 = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Violation_Points___25 = null;
					} else {
						this.Violation_Points___25 = dis.readDouble();
					}

					this.Violation_Detail___25 = readString(dis);

					this.Violation_Memo___25 = readString(dis);

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.end = readString(dis);

					this.Address = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// String

				writeString(this.Inspection_Date, dos);

				// Integer

				writeInteger(this.Inspection_Score, dos);

				// Integer

				writeInteger(this.Street_Number, dos);

				// String

				writeString(this.Street_Name, dos);

				// String

				writeString(this.Street_Direction, dos);

				// String

				writeString(this.Street_Type, dos);

				// String

				writeString(this.Street_Unit, dos);

				// String

				writeString(this.Street_Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// String

				writeString(this.Violation_Description___1, dos);

				// Double

				if (this.Violation_Points___1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___1);
				}

				// String

				writeString(this.Violation_Detail___1, dos);

				// String

				writeString(this.Violation_Memo___1, dos);

				// String

				writeString(this.Violation_Description___2, dos);

				// Double

				if (this.Violation_Points___2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___2);
				}

				// String

				writeString(this.Violation_Detail___2, dos);

				// String

				writeString(this.Violation_Memo___2, dos);

				// String

				writeString(this.Violation_Description___3, dos);

				// Double

				if (this.Violation_Points___3 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___3);
				}

				// String

				writeString(this.Violation_Detail___3, dos);

				// String

				writeString(this.Violation_Memo___3, dos);

				// String

				writeString(this.Violation_Description___4, dos);

				// Double

				if (this.Violation_Points___4 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___4);
				}

				// String

				writeString(this.Violation_Detail___4, dos);

				// String

				writeString(this.Violation_Memo___4, dos);

				// String

				writeString(this.Violation_Description___5, dos);

				// Double

				if (this.Violation_Points___5 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___5);
				}

				// String

				writeString(this.Violation_Detail___5, dos);

				// String

				writeString(this.Violation_Memo___5, dos);

				// String

				writeString(this.Violation_Description___6, dos);

				// Double

				if (this.Violation_Points___6 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___6);
				}

				// String

				writeString(this.Violation_Detail___6, dos);

				// String

				writeString(this.Violation_Memo___6, dos);

				// String

				writeString(this.Violation_Description___7, dos);

				// Double

				if (this.Violation_Points___7 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___7);
				}

				// String

				writeString(this.Violation_Detail___7, dos);

				// String

				writeString(this.Violation_Memo___7, dos);

				// String

				writeString(this.Violation_Description___8, dos);

				// Double

				if (this.Violation_Points___8 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___8);
				}

				// String

				writeString(this.Violation_Detail___8, dos);

				// String

				writeString(this.Violation_Memo___8, dos);

				// String

				writeString(this.Violation_Description___9, dos);

				// Double

				if (this.Violation_Points___9 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___9);
				}

				// String

				writeString(this.Violation_Detail___9, dos);

				// String

				writeString(this.Violation_Memo___9, dos);

				// String

				writeString(this.Violation_Description___10, dos);

				// Double

				if (this.Violation_Points___10 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___10);
				}

				// String

				writeString(this.Violation_Detail___10, dos);

				// String

				writeString(this.Violation_Memo___10, dos);

				// String

				writeString(this.Violation_Description___11, dos);

				// Double

				if (this.Violation_Points___11 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___11);
				}

				// String

				writeString(this.Violation_Detail___11, dos);

				// String

				writeString(this.Violation_Memo___11, dos);

				// String

				writeString(this.Violation_Description___12, dos);

				// Double

				if (this.Violation_Points___12 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___12);
				}

				// String

				writeString(this.Violation_Detail___12, dos);

				// String

				writeString(this.Violation_Memo___12, dos);

				// String

				writeString(this.Violation_Description___13, dos);

				// Double

				if (this.Violation_Points___13 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___13);
				}

				// String

				writeString(this.Violation_Detail___13, dos);

				// String

				writeString(this.Violation_Memo___13, dos);

				// String

				writeString(this.Violation_Description___14, dos);

				// Double

				if (this.Violation_Points___14 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___14);
				}

				// String

				writeString(this.Violation_Detail___14, dos);

				// String

				writeString(this.Violation_Memo___14, dos);

				// String

				writeString(this.Violation_Description___15, dos);

				// Double

				if (this.Violation_Points___15 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___15);
				}

				// String

				writeString(this.Violation_Detail___15, dos);

				// String

				writeString(this.Violation_Memo___15, dos);

				// String

				writeString(this.Violation_Description___16, dos);

				// Double

				if (this.Violation_Points___16 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___16);
				}

				// String

				writeString(this.Violation_Detail___16, dos);

				// String

				writeString(this.Violation_Memo___16, dos);

				// String

				writeString(this.Violation_Description___17, dos);

				// Double

				if (this.Violation_Points___17 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___17);
				}

				// String

				writeString(this.Violation_Detail___17, dos);

				// String

				writeString(this.Violation_Memo___17, dos);

				// String

				writeString(this.Violation_Description___18, dos);

				// Double

				if (this.Violation_Points___18 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___18);
				}

				// String

				writeString(this.Violation_Detail___18, dos);

				// String

				writeString(this.Violation_Memo___18, dos);

				// String

				writeString(this.Violation_Description___19, dos);

				// Double

				if (this.Violation_Points___19 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___19);
				}

				// String

				writeString(this.Violation_Detail___19, dos);

				// String

				writeString(this.Violation_Memo___19, dos);

				// String

				writeString(this.Violation_Description___20, dos);

				// Double

				if (this.Violation_Points___20 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___20);
				}

				// String

				writeString(this.Violation_Detail___20, dos);

				// String

				writeString(this.Violation__Memo___20, dos);

				// String

				writeString(this.Violation_Description___21, dos);

				// Double

				if (this.Violation_Points___21 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___21);
				}

				// String

				writeString(this.Violation_Detail___21, dos);

				// String

				writeString(this.Violation_Memo___21, dos);

				// String

				writeString(this.Violation_Description___22, dos);

				// Double

				if (this.Violation_Points___22 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___22);
				}

				// String

				writeString(this.Violation_Detail___22, dos);

				// String

				writeString(this.Violation_Memo___22, dos);

				// String

				writeString(this.Violation_Description___23, dos);

				// Double

				if (this.Violation_Points___23 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___23);
				}

				// String

				writeString(this.Violation_Detail___23, dos);

				// String

				writeString(this.Violation_Memo___23, dos);

				// String

				writeString(this.Violation_Description___24, dos);

				// Double

				if (this.Violation_Points___24 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___24);
				}

				// String

				writeString(this.Violation_Detail___24, dos);

				// String

				writeString(this.Violation_Memo___24, dos);

				// String

				writeString(this.Violation_Description___25, dos);

				// Double

				if (this.Violation_Points___25 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___25);
				}

				// String

				writeString(this.Violation_Detail___25, dos);

				// String

				writeString(this.Violation_Memo___25, dos);

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.end, dos);

				// String

				writeString(this.Address, dos);

				// BigDecimal

				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.writeObject(this.Longitude);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// String

				writeString(this.Inspection_Date, dos);

				// Integer

				writeInteger(this.Inspection_Score, dos);

				// Integer

				writeInteger(this.Street_Number, dos);

				// String

				writeString(this.Street_Name, dos);

				// String

				writeString(this.Street_Direction, dos);

				// String

				writeString(this.Street_Type, dos);

				// String

				writeString(this.Street_Unit, dos);

				// String

				writeString(this.Street_Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// String

				writeString(this.Violation_Description___1, dos);

				// Double

				if (this.Violation_Points___1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___1);
				}

				// String

				writeString(this.Violation_Detail___1, dos);

				// String

				writeString(this.Violation_Memo___1, dos);

				// String

				writeString(this.Violation_Description___2, dos);

				// Double

				if (this.Violation_Points___2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___2);
				}

				// String

				writeString(this.Violation_Detail___2, dos);

				// String

				writeString(this.Violation_Memo___2, dos);

				// String

				writeString(this.Violation_Description___3, dos);

				// Double

				if (this.Violation_Points___3 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___3);
				}

				// String

				writeString(this.Violation_Detail___3, dos);

				// String

				writeString(this.Violation_Memo___3, dos);

				// String

				writeString(this.Violation_Description___4, dos);

				// Double

				if (this.Violation_Points___4 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___4);
				}

				// String

				writeString(this.Violation_Detail___4, dos);

				// String

				writeString(this.Violation_Memo___4, dos);

				// String

				writeString(this.Violation_Description___5, dos);

				// Double

				if (this.Violation_Points___5 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___5);
				}

				// String

				writeString(this.Violation_Detail___5, dos);

				// String

				writeString(this.Violation_Memo___5, dos);

				// String

				writeString(this.Violation_Description___6, dos);

				// Double

				if (this.Violation_Points___6 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___6);
				}

				// String

				writeString(this.Violation_Detail___6, dos);

				// String

				writeString(this.Violation_Memo___6, dos);

				// String

				writeString(this.Violation_Description___7, dos);

				// Double

				if (this.Violation_Points___7 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___7);
				}

				// String

				writeString(this.Violation_Detail___7, dos);

				// String

				writeString(this.Violation_Memo___7, dos);

				// String

				writeString(this.Violation_Description___8, dos);

				// Double

				if (this.Violation_Points___8 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___8);
				}

				// String

				writeString(this.Violation_Detail___8, dos);

				// String

				writeString(this.Violation_Memo___8, dos);

				// String

				writeString(this.Violation_Description___9, dos);

				// Double

				if (this.Violation_Points___9 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___9);
				}

				// String

				writeString(this.Violation_Detail___9, dos);

				// String

				writeString(this.Violation_Memo___9, dos);

				// String

				writeString(this.Violation_Description___10, dos);

				// Double

				if (this.Violation_Points___10 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___10);
				}

				// String

				writeString(this.Violation_Detail___10, dos);

				// String

				writeString(this.Violation_Memo___10, dos);

				// String

				writeString(this.Violation_Description___11, dos);

				// Double

				if (this.Violation_Points___11 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___11);
				}

				// String

				writeString(this.Violation_Detail___11, dos);

				// String

				writeString(this.Violation_Memo___11, dos);

				// String

				writeString(this.Violation_Description___12, dos);

				// Double

				if (this.Violation_Points___12 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___12);
				}

				// String

				writeString(this.Violation_Detail___12, dos);

				// String

				writeString(this.Violation_Memo___12, dos);

				// String

				writeString(this.Violation_Description___13, dos);

				// Double

				if (this.Violation_Points___13 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___13);
				}

				// String

				writeString(this.Violation_Detail___13, dos);

				// String

				writeString(this.Violation_Memo___13, dos);

				// String

				writeString(this.Violation_Description___14, dos);

				// Double

				if (this.Violation_Points___14 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___14);
				}

				// String

				writeString(this.Violation_Detail___14, dos);

				// String

				writeString(this.Violation_Memo___14, dos);

				// String

				writeString(this.Violation_Description___15, dos);

				// Double

				if (this.Violation_Points___15 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___15);
				}

				// String

				writeString(this.Violation_Detail___15, dos);

				// String

				writeString(this.Violation_Memo___15, dos);

				// String

				writeString(this.Violation_Description___16, dos);

				// Double

				if (this.Violation_Points___16 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___16);
				}

				// String

				writeString(this.Violation_Detail___16, dos);

				// String

				writeString(this.Violation_Memo___16, dos);

				// String

				writeString(this.Violation_Description___17, dos);

				// Double

				if (this.Violation_Points___17 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___17);
				}

				// String

				writeString(this.Violation_Detail___17, dos);

				// String

				writeString(this.Violation_Memo___17, dos);

				// String

				writeString(this.Violation_Description___18, dos);

				// Double

				if (this.Violation_Points___18 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___18);
				}

				// String

				writeString(this.Violation_Detail___18, dos);

				// String

				writeString(this.Violation_Memo___18, dos);

				// String

				writeString(this.Violation_Description___19, dos);

				// Double

				if (this.Violation_Points___19 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___19);
				}

				// String

				writeString(this.Violation_Detail___19, dos);

				// String

				writeString(this.Violation_Memo___19, dos);

				// String

				writeString(this.Violation_Description___20, dos);

				// Double

				if (this.Violation_Points___20 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___20);
				}

				// String

				writeString(this.Violation_Detail___20, dos);

				// String

				writeString(this.Violation__Memo___20, dos);

				// String

				writeString(this.Violation_Description___21, dos);

				// Double

				if (this.Violation_Points___21 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___21);
				}

				// String

				writeString(this.Violation_Detail___21, dos);

				// String

				writeString(this.Violation_Memo___21, dos);

				// String

				writeString(this.Violation_Description___22, dos);

				// Double

				if (this.Violation_Points___22 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___22);
				}

				// String

				writeString(this.Violation_Detail___22, dos);

				// String

				writeString(this.Violation_Memo___22, dos);

				// String

				writeString(this.Violation_Description___23, dos);

				// Double

				if (this.Violation_Points___23 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___23);
				}

				// String

				writeString(this.Violation_Detail___23, dos);

				// String

				writeString(this.Violation_Memo___23, dos);

				// String

				writeString(this.Violation_Description___24, dos);

				// Double

				if (this.Violation_Points___24 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___24);
				}

				// String

				writeString(this.Violation_Detail___24, dos);

				// String

				writeString(this.Violation_Memo___24, dos);

				// String

				writeString(this.Violation_Description___25, dos);

				// Double

				if (this.Violation_Points___25 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Violation_Points___25);
				}

				// String

				writeString(this.Violation_Detail___25, dos);

				// String

				writeString(this.Violation_Memo___25, dos);

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.end, dos);

				// String

				writeString(this.Address, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Longitude);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Inspection_ID=" + Inspection_ID);
			sb.append(",Restaurant_Name=" + Restaurant_Name);
			sb.append(",Inspection_Type=" + Inspection_Type);
			sb.append(",Inspection_Date=" + Inspection_Date);
			sb.append(",Inspection_Score=" + String.valueOf(Inspection_Score));
			sb.append(",Street_Number=" + String.valueOf(Street_Number));
			sb.append(",Street_Name=" + Street_Name);
			sb.append(",Street_Direction=" + Street_Direction);
			sb.append(",Street_Type=" + Street_Type);
			sb.append(",Street_Unit=" + Street_Unit);
			sb.append(",Street_Address=" + Street_Address);
			sb.append(",Zip_Code=" + Zip_Code);
			sb.append(",Violation_Description___1=" + Violation_Description___1);
			sb.append(",Violation_Points___1=" + String.valueOf(Violation_Points___1));
			sb.append(",Violation_Detail___1=" + Violation_Detail___1);
			sb.append(",Violation_Memo___1=" + Violation_Memo___1);
			sb.append(",Violation_Description___2=" + Violation_Description___2);
			sb.append(",Violation_Points___2=" + String.valueOf(Violation_Points___2));
			sb.append(",Violation_Detail___2=" + Violation_Detail___2);
			sb.append(",Violation_Memo___2=" + Violation_Memo___2);
			sb.append(",Violation_Description___3=" + Violation_Description___3);
			sb.append(",Violation_Points___3=" + String.valueOf(Violation_Points___3));
			sb.append(",Violation_Detail___3=" + Violation_Detail___3);
			sb.append(",Violation_Memo___3=" + Violation_Memo___3);
			sb.append(",Violation_Description___4=" + Violation_Description___4);
			sb.append(",Violation_Points___4=" + String.valueOf(Violation_Points___4));
			sb.append(",Violation_Detail___4=" + Violation_Detail___4);
			sb.append(",Violation_Memo___4=" + Violation_Memo___4);
			sb.append(",Violation_Description___5=" + Violation_Description___5);
			sb.append(",Violation_Points___5=" + String.valueOf(Violation_Points___5));
			sb.append(",Violation_Detail___5=" + Violation_Detail___5);
			sb.append(",Violation_Memo___5=" + Violation_Memo___5);
			sb.append(",Violation_Description___6=" + Violation_Description___6);
			sb.append(",Violation_Points___6=" + String.valueOf(Violation_Points___6));
			sb.append(",Violation_Detail___6=" + Violation_Detail___6);
			sb.append(",Violation_Memo___6=" + Violation_Memo___6);
			sb.append(",Violation_Description___7=" + Violation_Description___7);
			sb.append(",Violation_Points___7=" + String.valueOf(Violation_Points___7));
			sb.append(",Violation_Detail___7=" + Violation_Detail___7);
			sb.append(",Violation_Memo___7=" + Violation_Memo___7);
			sb.append(",Violation_Description___8=" + Violation_Description___8);
			sb.append(",Violation_Points___8=" + String.valueOf(Violation_Points___8));
			sb.append(",Violation_Detail___8=" + Violation_Detail___8);
			sb.append(",Violation_Memo___8=" + Violation_Memo___8);
			sb.append(",Violation_Description___9=" + Violation_Description___9);
			sb.append(",Violation_Points___9=" + String.valueOf(Violation_Points___9));
			sb.append(",Violation_Detail___9=" + Violation_Detail___9);
			sb.append(",Violation_Memo___9=" + Violation_Memo___9);
			sb.append(",Violation_Description___10=" + Violation_Description___10);
			sb.append(",Violation_Points___10=" + String.valueOf(Violation_Points___10));
			sb.append(",Violation_Detail___10=" + Violation_Detail___10);
			sb.append(",Violation_Memo___10=" + Violation_Memo___10);
			sb.append(",Violation_Description___11=" + Violation_Description___11);
			sb.append(",Violation_Points___11=" + String.valueOf(Violation_Points___11));
			sb.append(",Violation_Detail___11=" + Violation_Detail___11);
			sb.append(",Violation_Memo___11=" + Violation_Memo___11);
			sb.append(",Violation_Description___12=" + Violation_Description___12);
			sb.append(",Violation_Points___12=" + String.valueOf(Violation_Points___12));
			sb.append(",Violation_Detail___12=" + Violation_Detail___12);
			sb.append(",Violation_Memo___12=" + Violation_Memo___12);
			sb.append(",Violation_Description___13=" + Violation_Description___13);
			sb.append(",Violation_Points___13=" + String.valueOf(Violation_Points___13));
			sb.append(",Violation_Detail___13=" + Violation_Detail___13);
			sb.append(",Violation_Memo___13=" + Violation_Memo___13);
			sb.append(",Violation_Description___14=" + Violation_Description___14);
			sb.append(",Violation_Points___14=" + String.valueOf(Violation_Points___14));
			sb.append(",Violation_Detail___14=" + Violation_Detail___14);
			sb.append(",Violation_Memo___14=" + Violation_Memo___14);
			sb.append(",Violation_Description___15=" + Violation_Description___15);
			sb.append(",Violation_Points___15=" + String.valueOf(Violation_Points___15));
			sb.append(",Violation_Detail___15=" + Violation_Detail___15);
			sb.append(",Violation_Memo___15=" + Violation_Memo___15);
			sb.append(",Violation_Description___16=" + Violation_Description___16);
			sb.append(",Violation_Points___16=" + String.valueOf(Violation_Points___16));
			sb.append(",Violation_Detail___16=" + Violation_Detail___16);
			sb.append(",Violation_Memo___16=" + Violation_Memo___16);
			sb.append(",Violation_Description___17=" + Violation_Description___17);
			sb.append(",Violation_Points___17=" + String.valueOf(Violation_Points___17));
			sb.append(",Violation_Detail___17=" + Violation_Detail___17);
			sb.append(",Violation_Memo___17=" + Violation_Memo___17);
			sb.append(",Violation_Description___18=" + Violation_Description___18);
			sb.append(",Violation_Points___18=" + String.valueOf(Violation_Points___18));
			sb.append(",Violation_Detail___18=" + Violation_Detail___18);
			sb.append(",Violation_Memo___18=" + Violation_Memo___18);
			sb.append(",Violation_Description___19=" + Violation_Description___19);
			sb.append(",Violation_Points___19=" + String.valueOf(Violation_Points___19));
			sb.append(",Violation_Detail___19=" + Violation_Detail___19);
			sb.append(",Violation_Memo___19=" + Violation_Memo___19);
			sb.append(",Violation_Description___20=" + Violation_Description___20);
			sb.append(",Violation_Points___20=" + String.valueOf(Violation_Points___20));
			sb.append(",Violation_Detail___20=" + Violation_Detail___20);
			sb.append(",Violation__Memo___20=" + Violation__Memo___20);
			sb.append(",Violation_Description___21=" + Violation_Description___21);
			sb.append(",Violation_Points___21=" + String.valueOf(Violation_Points___21));
			sb.append(",Violation_Detail___21=" + Violation_Detail___21);
			sb.append(",Violation_Memo___21=" + Violation_Memo___21);
			sb.append(",Violation_Description___22=" + Violation_Description___22);
			sb.append(",Violation_Points___22=" + String.valueOf(Violation_Points___22));
			sb.append(",Violation_Detail___22=" + Violation_Detail___22);
			sb.append(",Violation_Memo___22=" + Violation_Memo___22);
			sb.append(",Violation_Description___23=" + Violation_Description___23);
			sb.append(",Violation_Points___23=" + String.valueOf(Violation_Points___23));
			sb.append(",Violation_Detail___23=" + Violation_Detail___23);
			sb.append(",Violation_Memo___23=" + Violation_Memo___23);
			sb.append(",Violation_Description___24=" + Violation_Description___24);
			sb.append(",Violation_Points___24=" + String.valueOf(Violation_Points___24));
			sb.append(",Violation_Detail___24=" + Violation_Detail___24);
			sb.append(",Violation_Memo___24=" + Violation_Memo___24);
			sb.append(",Violation_Description___25=" + Violation_Description___25);
			sb.append(",Violation_Points___25=" + String.valueOf(Violation_Points___25));
			sb.append(",Violation_Detail___25=" + Violation_Detail___25);
			sb.append(",Violation_Memo___25=" + Violation_Memo___25);
			sb.append(",Inspection_Month=" + Inspection_Month);
			sb.append(",Inspection_Year=" + Inspection_Year);
			sb.append(",end=" + end);
			sb.append(",Address=" + Address);
			sb.append(",Latitude=" + String.valueOf(Latitude));
			sb.append(",Longitude=" + String.valueOf(Longitude));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Inspection_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_ID);
			}

			sb.append("|");

			if (Restaurant_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Restaurant_Name);
			}

			sb.append("|");

			if (Inspection_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Type);
			}

			sb.append("|");

			if (Inspection_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Date);
			}

			sb.append("|");

			if (Inspection_Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Score);
			}

			sb.append("|");

			if (Street_Number == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Number);
			}

			sb.append("|");

			if (Street_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Name);
			}

			sb.append("|");

			if (Street_Direction == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Direction);
			}

			sb.append("|");

			if (Street_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Type);
			}

			sb.append("|");

			if (Street_Unit == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Unit);
			}

			sb.append("|");

			if (Street_Address == null) {
				sb.append("<null>");
			} else {
				sb.append(Street_Address);
			}

			sb.append("|");

			if (Zip_Code == null) {
				sb.append("<null>");
			} else {
				sb.append(Zip_Code);
			}

			sb.append("|");

			if (Violation_Description___1 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___1);
			}

			sb.append("|");

			if (Violation_Points___1 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___1);
			}

			sb.append("|");

			if (Violation_Detail___1 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___1);
			}

			sb.append("|");

			if (Violation_Memo___1 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___1);
			}

			sb.append("|");

			if (Violation_Description___2 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___2);
			}

			sb.append("|");

			if (Violation_Points___2 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___2);
			}

			sb.append("|");

			if (Violation_Detail___2 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___2);
			}

			sb.append("|");

			if (Violation_Memo___2 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___2);
			}

			sb.append("|");

			if (Violation_Description___3 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___3);
			}

			sb.append("|");

			if (Violation_Points___3 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___3);
			}

			sb.append("|");

			if (Violation_Detail___3 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___3);
			}

			sb.append("|");

			if (Violation_Memo___3 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___3);
			}

			sb.append("|");

			if (Violation_Description___4 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___4);
			}

			sb.append("|");

			if (Violation_Points___4 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___4);
			}

			sb.append("|");

			if (Violation_Detail___4 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___4);
			}

			sb.append("|");

			if (Violation_Memo___4 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___4);
			}

			sb.append("|");

			if (Violation_Description___5 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___5);
			}

			sb.append("|");

			if (Violation_Points___5 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___5);
			}

			sb.append("|");

			if (Violation_Detail___5 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___5);
			}

			sb.append("|");

			if (Violation_Memo___5 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___5);
			}

			sb.append("|");

			if (Violation_Description___6 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___6);
			}

			sb.append("|");

			if (Violation_Points___6 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___6);
			}

			sb.append("|");

			if (Violation_Detail___6 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___6);
			}

			sb.append("|");

			if (Violation_Memo___6 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___6);
			}

			sb.append("|");

			if (Violation_Description___7 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___7);
			}

			sb.append("|");

			if (Violation_Points___7 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___7);
			}

			sb.append("|");

			if (Violation_Detail___7 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___7);
			}

			sb.append("|");

			if (Violation_Memo___7 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___7);
			}

			sb.append("|");

			if (Violation_Description___8 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___8);
			}

			sb.append("|");

			if (Violation_Points___8 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___8);
			}

			sb.append("|");

			if (Violation_Detail___8 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___8);
			}

			sb.append("|");

			if (Violation_Memo___8 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___8);
			}

			sb.append("|");

			if (Violation_Description___9 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___9);
			}

			sb.append("|");

			if (Violation_Points___9 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___9);
			}

			sb.append("|");

			if (Violation_Detail___9 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___9);
			}

			sb.append("|");

			if (Violation_Memo___9 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___9);
			}

			sb.append("|");

			if (Violation_Description___10 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___10);
			}

			sb.append("|");

			if (Violation_Points___10 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___10);
			}

			sb.append("|");

			if (Violation_Detail___10 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___10);
			}

			sb.append("|");

			if (Violation_Memo___10 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___10);
			}

			sb.append("|");

			if (Violation_Description___11 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___11);
			}

			sb.append("|");

			if (Violation_Points___11 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___11);
			}

			sb.append("|");

			if (Violation_Detail___11 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___11);
			}

			sb.append("|");

			if (Violation_Memo___11 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___11);
			}

			sb.append("|");

			if (Violation_Description___12 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___12);
			}

			sb.append("|");

			if (Violation_Points___12 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___12);
			}

			sb.append("|");

			if (Violation_Detail___12 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___12);
			}

			sb.append("|");

			if (Violation_Memo___12 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___12);
			}

			sb.append("|");

			if (Violation_Description___13 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___13);
			}

			sb.append("|");

			if (Violation_Points___13 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___13);
			}

			sb.append("|");

			if (Violation_Detail___13 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___13);
			}

			sb.append("|");

			if (Violation_Memo___13 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___13);
			}

			sb.append("|");

			if (Violation_Description___14 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___14);
			}

			sb.append("|");

			if (Violation_Points___14 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___14);
			}

			sb.append("|");

			if (Violation_Detail___14 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___14);
			}

			sb.append("|");

			if (Violation_Memo___14 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___14);
			}

			sb.append("|");

			if (Violation_Description___15 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___15);
			}

			sb.append("|");

			if (Violation_Points___15 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___15);
			}

			sb.append("|");

			if (Violation_Detail___15 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___15);
			}

			sb.append("|");

			if (Violation_Memo___15 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___15);
			}

			sb.append("|");

			if (Violation_Description___16 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___16);
			}

			sb.append("|");

			if (Violation_Points___16 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___16);
			}

			sb.append("|");

			if (Violation_Detail___16 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___16);
			}

			sb.append("|");

			if (Violation_Memo___16 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___16);
			}

			sb.append("|");

			if (Violation_Description___17 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___17);
			}

			sb.append("|");

			if (Violation_Points___17 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___17);
			}

			sb.append("|");

			if (Violation_Detail___17 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___17);
			}

			sb.append("|");

			if (Violation_Memo___17 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___17);
			}

			sb.append("|");

			if (Violation_Description___18 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___18);
			}

			sb.append("|");

			if (Violation_Points___18 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___18);
			}

			sb.append("|");

			if (Violation_Detail___18 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___18);
			}

			sb.append("|");

			if (Violation_Memo___18 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___18);
			}

			sb.append("|");

			if (Violation_Description___19 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___19);
			}

			sb.append("|");

			if (Violation_Points___19 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___19);
			}

			sb.append("|");

			if (Violation_Detail___19 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___19);
			}

			sb.append("|");

			if (Violation_Memo___19 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___19);
			}

			sb.append("|");

			if (Violation_Description___20 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___20);
			}

			sb.append("|");

			if (Violation_Points___20 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___20);
			}

			sb.append("|");

			if (Violation_Detail___20 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___20);
			}

			sb.append("|");

			if (Violation__Memo___20 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation__Memo___20);
			}

			sb.append("|");

			if (Violation_Description___21 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___21);
			}

			sb.append("|");

			if (Violation_Points___21 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___21);
			}

			sb.append("|");

			if (Violation_Detail___21 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___21);
			}

			sb.append("|");

			if (Violation_Memo___21 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___21);
			}

			sb.append("|");

			if (Violation_Description___22 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___22);
			}

			sb.append("|");

			if (Violation_Points___22 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___22);
			}

			sb.append("|");

			if (Violation_Detail___22 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___22);
			}

			sb.append("|");

			if (Violation_Memo___22 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___22);
			}

			sb.append("|");

			if (Violation_Description___23 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___23);
			}

			sb.append("|");

			if (Violation_Points___23 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___23);
			}

			sb.append("|");

			if (Violation_Detail___23 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___23);
			}

			sb.append("|");

			if (Violation_Memo___23 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___23);
			}

			sb.append("|");

			if (Violation_Description___24 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___24);
			}

			sb.append("|");

			if (Violation_Points___24 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___24);
			}

			sb.append("|");

			if (Violation_Detail___24 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___24);
			}

			sb.append("|");

			if (Violation_Memo___24 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___24);
			}

			sb.append("|");

			if (Violation_Description___25 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Description___25);
			}

			sb.append("|");

			if (Violation_Points___25 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Points___25);
			}

			sb.append("|");

			if (Violation_Detail___25 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Detail___25);
			}

			sb.append("|");

			if (Violation_Memo___25 == null) {
				sb.append("<null>");
			} else {
				sb.append(Violation_Memo___25);
			}

			sb.append("|");

			if (Inspection_Month == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Month);
			}

			sb.append("|");

			if (Inspection_Year == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Year);
			}

			sb.append("|");

			if (end == null) {
				sb.append("<null>");
			} else {
				sb.append(end);
			}

			sb.append("|");

			if (Address == null) {
				sb.append("<null>");
			} else {
				sb.append(Address);
			}

			sb.append("|");

			if (Latitude == null) {
				sb.append("<null>");
			} else {
				sb.append(Latitude);
			}

			sb.append("|");

			if (Longitude == null) {
				sb.append("<null>");
			} else {
				sb.append(Longitude);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tFileInputDelimited_1");
		org.slf4j.MDC.put("_subJobPid", "oFwGQa_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				load_stg_dallasStruct load_stg_dallas = new load_stg_dallasStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				cLabel = "target_mysql";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "load_stg_dallas");

				int tos_count_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_1 = new StringBuilder();
							log4jParamters_tDBOutput_1.append("Parameters:");
							log4jParamters_tDBOutput_1.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DBNAME" + " = " + "\"midterm\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USER" + " = " + "\"root\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:r7kLZWBXIzfRAZuvKNbrrfvwsR+ISNHcDAqXzDcV38crowuPyun4kw==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"stg_finaldallasconnectiontable\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "DROP_IF_EXISTS_AND_CREATE");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_1 - " + (log4jParamters_tDBOutput_1));
						}
					}
					new BytesLimit65535_tDBOutput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_1", "target_mysql", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				String tableName_tDBOutput_1 = "stg_finaldallasconnectiontable";
				boolean whetherReject_tDBOutput_1 = false;

				java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
				calendar_tDBOutput_1.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				calendar_tDBOutput_1.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				long date_tDBOutput_1;

				java.sql.Connection conn_tDBOutput_1 = null;

				String properties_tDBOutput_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_1 == null || properties_tDBOutput_1.trim().length() == 0) {
					properties_tDBOutput_1 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_1.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_1 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_1.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_1 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "midterm" + "?"
						+ properties_tDBOutput_1;

				String driverClass_tDBOutput_1 = "com.mysql.cj.jdbc.Driver";

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Driver ClassName: ") + (driverClass_tDBOutput_1) + ("."));
				String dbUser_tDBOutput_1 = "root";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:8auUjDq96tnLFnQwBaIyUfGgfbPF/pGBAdC2H+MhT6iW/oCUMiyYVQ==");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;
				java.lang.Class.forName(driverClass_tDBOutput_1);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection attempts to '") + (url_tDBOutput_1)
							+ ("' with the username '") + (dbUser_tDBOutput_1) + ("'."));
				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection to '") + (url_tDBOutput_1) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);

				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_1.getAutoCommit()) + ("'."));

				int count_tDBOutput_1 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_1 = conn_tDBOutput_1.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_1 = dbMetaData_tDBOutput_1.getTables("midterm", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_1 = false;
				while (rsTable_tDBOutput_1.next()) {
					String table_tDBOutput_1 = rsTable_tDBOutput_1.getString("TABLE_NAME");
					if (table_tDBOutput_1.equalsIgnoreCase("stg_finaldallasconnectiontable")) {
						whetherExist_tDBOutput_1 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_1) {
					try (java.sql.Statement stmtDrop_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
						if (log.isDebugEnabled())
							log.debug(
									"tDBOutput_1 - " + ("Dropping") + (" table '") + (tableName_tDBOutput_1) + ("'."));
						stmtDrop_tDBOutput_1.execute("DROP TABLE `" + tableName_tDBOutput_1 + "`");
						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Drop") + (" table '") + (tableName_tDBOutput_1)
									+ ("' has succeeded."));
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Creating") + (" table '") + (tableName_tDBOutput_1) + ("'."));
					stmtCreate_tDBOutput_1.execute("CREATE TABLE `" + tableName_tDBOutput_1
							+ "`(`Inspection_ID` VARCHAR(10)  ,`Restaurant_Name` VARCHAR(65)  ,`Inspection_Type` VARCHAR(9)  ,`Inspection_Date` DATETIME ,`Inspection_Score` INT(3)  ,`Street_Number` INT(5)  ,`Street_Name` VARCHAR(25)  ,`Street_Direction` VARCHAR(3)  ,`Street_Type` VARCHAR(4)  ,`Street_Unit` VARCHAR(5)  ,`Street_Address` VARCHAR(37)  ,`Zip_Code` VARCHAR(10)  ,`Violation_Points___1` DOUBLE(3,0)  ,`Violation_Points___2` DOUBLE(3,0)  ,`Violation_Points___3` DOUBLE(3,0)  ,`Violation_Points___4` DOUBLE(3,0)  ,`Violation_Points___5` DOUBLE(3,0)  ,`Violation_Points___6` DOUBLE(3,0)  ,`Violation_Points___7` DOUBLE(3,0)  ,`Violation_Points___8` DOUBLE(3,0)  ,`Violation_Points___9` DOUBLE(3,0)  ,`Violation_Points___10` DOUBLE(3,0)  ,`Violation_Points___11` DOUBLE(3,0)  ,`Violation_Points___12` DOUBLE(3,0)  ,`Violation_Points___13` DOUBLE(3,0)  ,`Violation_Points___14` DOUBLE(3,0)  ,`Violation_Points___15` DOUBLE(3,0)  ,`Violation_Points___16` DOUBLE(3,0)  ,`Violation_Points___17` DOUBLE(3,0)  ,`Violation_Points___18` DOUBLE(3,0)  ,`Violation_Points___19` DOUBLE(3,0)  ,`Violation_Points___20` DOUBLE(3,0)  ,`Violation_Points___21` DOUBLE(3,0)  ,`Violation_Points___22` DOUBLE(3,0)  ,`Violation_Points___23` DOUBLE(3,0)  ,`Violation_Points___24` DOUBLE(3,0)  ,`Violation_Points___25` DOUBLE(3,0)  ,`Inspection_Month` VARCHAR(8)  ,`Inspection_Year` VARCHAR(6)  ,`Address` VARCHAR(250)  ,`Latitude` DECIMAL(65,8)  ,`Longitude` DECIMAL(65,8)  ,`DI_CreateDate` DATETIME ,`DI_WorkFlowFileNme` VARCHAR(300)  )");
					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Create") + (" table '") + (tableName_tDBOutput_1)
								+ ("' has succeeded."));
				}

				String insert_tDBOutput_1 = "INSERT INTO `" + "stg_finaldallasconnectiontable"
						+ "` (`Inspection_ID`,`Restaurant_Name`,`Inspection_Type`,`Inspection_Date`,`Inspection_Score`,`Street_Number`,`Street_Name`,`Street_Direction`,`Street_Type`,`Street_Unit`,`Street_Address`,`Zip_Code`,`Violation_Points___1`,`Violation_Points___2`,`Violation_Points___3`,`Violation_Points___4`,`Violation_Points___5`,`Violation_Points___6`,`Violation_Points___7`,`Violation_Points___8`,`Violation_Points___9`,`Violation_Points___10`,`Violation_Points___11`,`Violation_Points___12`,`Violation_Points___13`,`Violation_Points___14`,`Violation_Points___15`,`Violation_Points___16`,`Violation_Points___17`,`Violation_Points___18`,`Violation_Points___19`,`Violation_Points___20`,`Violation_Points___21`,`Violation_Points___22`,`Violation_Points___23`,`Violation_Points___24`,`Violation_Points___25`,`Inspection_Month`,`Inspection_Year`,`Address`,`Latitude`,`Longitude`,`DI_CreateDate`,`DI_WorkFlowFileNme`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_1 = 100;
				int batchSizeCounter_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_1 = new StringBuilder();
							log4jParamters_tMap_1.append("Parameters:");
							log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_1 - " + (log4jParamters_tMap_1));
						}
					}
					new BytesLimit65535_tMap_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
					talendJobLogProcess(globalMap);
				}

// ###############################
// # Lookup's keys initialization
				int count_row1_tMap_1 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_load_stg_dallas_tMap_1 = 0;

				load_stg_dallasStruct load_stg_dallas_tmp = new load_stg_dallasStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				cLabel = "dallas_raw";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
							log4jParamters_tFileInputDelimited_1.append("Parameters:");
							log4jParamters_tFileInputDelimited_1.append("USE_EXISTING_DYNAMIC" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append(
									"FILENAME" + " = " + "\"C:/Users/siddh/Downloads/fin_dalas_staging_ready.xls\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSV_OPTION" + " = " + "true");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSVROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FIELDSEPARATOR" + " = " + "\",\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ESCAPE_CHAR" + " = " + "\"\\\"\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TEXT_ENCLOSURE" + " = " + "\"\\\"\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("HEADER" + " = " + "1");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FOOTER" + " = " + "0");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("REMOVE_EMPTY_ROW" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("UNCOMPRESS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMALL" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMSELECT" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Inspection_ID") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Restaurant_Name") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Inspection_Type") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Inspection_Date") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Inspection_Score") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Number") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Name") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Direction") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Type") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Unit") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Street_Address") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Zip_Code") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___1") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___1") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___1") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___1") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___2") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___2") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___2") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___2") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___3") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___3") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___3") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___3") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___4") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___4") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___4") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___4") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___5") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___5") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___5") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___5") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___6") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___6") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___6") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___6") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___7") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___7") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___7") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___7") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___8") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___8") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___8") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___8") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___9") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___9") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___9") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___9") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___10") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___10") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___10") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___10") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___11") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___11") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___11") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___11") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___12") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___12") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___12") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___12") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___13") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___13") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___13") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___13") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___14") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___14") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___14") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___14") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___15") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___15") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___15") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___15") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___16") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___16") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___16") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___16") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___17") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___17") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___17") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___17") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___18") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___18") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___18") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___18") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___19") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___19") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___19") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___19") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___20") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___20") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___20") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation__Memo___20") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___21") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___21") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___21") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___21") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___22") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___22") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___22") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___22") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___23") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___23") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___23") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___23") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___24") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___24") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___24") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___24") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Description___25") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___25") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Detail___25") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Memo___25") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Inspection_Month") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Inspection_Year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("end")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Address") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("Latitude") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Longitude") + "}]");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_FIELDS_NUM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_DATE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENCODING" + " = " + "\"UTF-8\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENABLE_DECODE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("USE_HEADER_AS_IS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputDelimited_1 - " + (log4jParamters_tFileInputDelimited_1));
						}
					}
					new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputDelimited_1", "dallas_raw", "tFileInputDelimited");
					talendJobLogProcess(globalMap);
				}

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				class RowHelper_tFileInputDelimited_1 {

					public void valueToConn_0(String[] rowtFileInputDelimited_1, row1Struct row1)
							throws java.lang.Exception {

						char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
						// support passing value (property: Field Separator) by 'context.fs' or
						// 'globalMap.get("fs")'.
						if (((String) ",").length() > 0) {
							fieldSeparator_tFileInputDelimited_1_ListType = ((String) ",").toCharArray();
						} else {
							throw new IllegalArgumentException("Field Separator must be assigned a char.");
						}
						if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																													// line
																													// when
																													// row
																													// separator
																													// is
																													// '\n'

							row1.Inspection_ID = null;

							row1.Restaurant_Name = null;

							row1.Inspection_Type = null;

							row1.Inspection_Date = null;

							row1.Inspection_Score = null;

							row1.Street_Number = null;

							row1.Street_Name = null;

							row1.Street_Direction = null;

							row1.Street_Type = null;

							row1.Street_Unit = null;

							row1.Street_Address = null;

							row1.Zip_Code = null;

							row1.Violation_Description___1 = null;

							row1.Violation_Points___1 = null;

							row1.Violation_Detail___1 = null;

							row1.Violation_Memo___1 = null;

							row1.Violation_Description___2 = null;

							row1.Violation_Points___2 = null;

							row1.Violation_Detail___2 = null;

							row1.Violation_Memo___2 = null;

							row1.Violation_Description___3 = null;

							row1.Violation_Points___3 = null;

							row1.Violation_Detail___3 = null;

							row1.Violation_Memo___3 = null;

							row1.Violation_Description___4 = null;

							row1.Violation_Points___4 = null;

							row1.Violation_Detail___4 = null;

							row1.Violation_Memo___4 = null;

							row1.Violation_Description___5 = null;

							row1.Violation_Points___5 = null;

							row1.Violation_Detail___5 = null;

							row1.Violation_Memo___5 = null;

							row1.Violation_Description___6 = null;

							row1.Violation_Points___6 = null;

							row1.Violation_Detail___6 = null;

							row1.Violation_Memo___6 = null;

							row1.Violation_Description___7 = null;

							row1.Violation_Points___7 = null;

							row1.Violation_Detail___7 = null;

							row1.Violation_Memo___7 = null;

							row1.Violation_Description___8 = null;

							row1.Violation_Points___8 = null;

							row1.Violation_Detail___8 = null;

							row1.Violation_Memo___8 = null;

							row1.Violation_Description___9 = null;

							row1.Violation_Points___9 = null;

							row1.Violation_Detail___9 = null;

							row1.Violation_Memo___9 = null;

							row1.Violation_Description___10 = null;

							row1.Violation_Points___10 = null;

							row1.Violation_Detail___10 = null;

							row1.Violation_Memo___10 = null;

							row1.Violation_Description___11 = null;

							row1.Violation_Points___11 = null;

							row1.Violation_Detail___11 = null;

							row1.Violation_Memo___11 = null;

							row1.Violation_Description___12 = null;

							row1.Violation_Points___12 = null;

							row1.Violation_Detail___12 = null;

							row1.Violation_Memo___12 = null;

							row1.Violation_Description___13 = null;

							row1.Violation_Points___13 = null;

							row1.Violation_Detail___13 = null;

							row1.Violation_Memo___13 = null;

							row1.Violation_Description___14 = null;

							row1.Violation_Points___14 = null;

							row1.Violation_Detail___14 = null;

							row1.Violation_Memo___14 = null;

							row1.Violation_Description___15 = null;

							row1.Violation_Points___15 = null;

							row1.Violation_Detail___15 = null;

							row1.Violation_Memo___15 = null;

							row1.Violation_Description___16 = null;

							row1.Violation_Points___16 = null;

							row1.Violation_Detail___16 = null;

							row1.Violation_Memo___16 = null;

							row1.Violation_Description___17 = null;

							row1.Violation_Points___17 = null;

							row1.Violation_Detail___17 = null;

							row1.Violation_Memo___17 = null;

							row1.Violation_Description___18 = null;

							row1.Violation_Points___18 = null;

							row1.Violation_Detail___18 = null;

							row1.Violation_Memo___18 = null;

							row1.Violation_Description___19 = null;

							row1.Violation_Points___19 = null;

							row1.Violation_Detail___19 = null;

							row1.Violation_Memo___19 = null;

							row1.Violation_Description___20 = null;

							row1.Violation_Points___20 = null;

							row1.Violation_Detail___20 = null;

							row1.Violation__Memo___20 = null;

							row1.Violation_Description___21 = null;

							row1.Violation_Points___21 = null;

							row1.Violation_Detail___21 = null;

							row1.Violation_Memo___21 = null;

							row1.Violation_Description___22 = null;

							row1.Violation_Points___22 = null;

							row1.Violation_Detail___22 = null;

							row1.Violation_Memo___22 = null;

						} else {

							int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

							columnIndexWithD_tFileInputDelimited_1 = 0;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Inspection_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Inspection_ID = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 1;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Restaurant_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Restaurant_Name = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 2;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Inspection_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Inspection_Type = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 3;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Inspection_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Inspection_Date = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 4;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Inspection_Score = ParserUtils.parseTo_Integer(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Inspection_Score", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Inspection_Score = null;

								}

							} else {

								row1.Inspection_Score = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 5;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Street_Number = ParserUtils.parseTo_Integer(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Street_Number", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Street_Number = null;

								}

							} else {

								row1.Street_Number = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 6;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Street_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Street_Name = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 7;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Street_Direction = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Street_Direction = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 8;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Street_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Street_Type = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 9;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Street_Unit = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Street_Unit = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 10;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Street_Address = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Street_Address = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 11;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Zip_Code = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Zip_Code = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 12;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___1 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___1 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 13;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___1 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___1", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___1 = null;

								}

							} else {

								row1.Violation_Points___1 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 14;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___1 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___1 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 15;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___1 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___1 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 16;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___2 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___2 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 17;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___2 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___2", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___2 = null;

								}

							} else {

								row1.Violation_Points___2 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 18;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___2 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___2 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 19;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___2 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___2 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 20;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___3 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___3 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 21;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___3 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___3", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___3 = null;

								}

							} else {

								row1.Violation_Points___3 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 22;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___3 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___3 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 23;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___3 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___3 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 24;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___4 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___4 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 25;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___4 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___4", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___4 = null;

								}

							} else {

								row1.Violation_Points___4 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 26;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___4 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___4 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 27;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___4 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___4 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 28;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___5 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___5 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 29;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___5 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___5", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___5 = null;

								}

							} else {

								row1.Violation_Points___5 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 30;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___5 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___5 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 31;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___5 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___5 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 32;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___6 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___6 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 33;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___6 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___6", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___6 = null;

								}

							} else {

								row1.Violation_Points___6 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 34;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___6 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___6 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 35;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___6 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___6 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 36;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___7 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___7 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 37;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___7 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___7", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___7 = null;

								}

							} else {

								row1.Violation_Points___7 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 38;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___7 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___7 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 39;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___7 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___7 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 40;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___8 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___8 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 41;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___8 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___8", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___8 = null;

								}

							} else {

								row1.Violation_Points___8 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 42;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___8 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___8 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 43;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___8 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___8 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 44;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___9 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___9 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 45;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___9 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___9", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___9 = null;

								}

							} else {

								row1.Violation_Points___9 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 46;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___9 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___9 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 47;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___9 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___9 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 48;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___10 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___10 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 49;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___10 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___10", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___10 = null;

								}

							} else {

								row1.Violation_Points___10 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 50;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___10 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___10 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 51;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___10 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___10 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 52;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___11 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___11 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 53;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___11 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___11", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___11 = null;

								}

							} else {

								row1.Violation_Points___11 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 54;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___11 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___11 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 55;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___11 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___11 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 56;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___12 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___12 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 57;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___12 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___12", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___12 = null;

								}

							} else {

								row1.Violation_Points___12 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 58;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___12 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___12 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 59;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___12 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___12 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 60;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___13 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___13 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 61;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___13 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___13", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___13 = null;

								}

							} else {

								row1.Violation_Points___13 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 62;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___13 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___13 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 63;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___13 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___13 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 64;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___14 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___14 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 65;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___14 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___14", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___14 = null;

								}

							} else {

								row1.Violation_Points___14 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 66;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___14 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___14 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 67;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___14 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___14 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 68;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___15 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___15 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 69;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___15 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___15", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___15 = null;

								}

							} else {

								row1.Violation_Points___15 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 70;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___15 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___15 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 71;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___15 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___15 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 72;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___16 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___16 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 73;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___16 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___16", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___16 = null;

								}

							} else {

								row1.Violation_Points___16 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 74;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___16 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___16 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 75;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___16 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___16 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 76;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___17 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___17 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 77;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___17 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___17", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___17 = null;

								}

							} else {

								row1.Violation_Points___17 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 78;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___17 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___17 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 79;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___17 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___17 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 80;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___18 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___18 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 81;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___18 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___18", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___18 = null;

								}

							} else {

								row1.Violation_Points___18 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 82;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___18 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___18 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 83;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___18 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___18 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 84;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___19 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___19 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 85;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___19 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___19", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___19 = null;

								}

							} else {

								row1.Violation_Points___19 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 86;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___19 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___19 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 87;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___19 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___19 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 88;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___20 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___20 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 89;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___20 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___20", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___20 = null;

								}

							} else {

								row1.Violation_Points___20 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 90;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___20 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___20 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 91;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation__Memo___20 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation__Memo___20 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 92;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___21 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___21 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 93;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___21 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___21", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___21 = null;

								}

							} else {

								row1.Violation_Points___21 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 94;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___21 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___21 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 95;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___21 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___21 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 96;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___22 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___22 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 97;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___22 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___22", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___22 = null;

								}

							} else {

								row1.Violation_Points___22 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 98;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___22 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___22 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 99;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___22 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___22 = null;

							}

						}

					}

					public void valueToConn_1(String[] rowtFileInputDelimited_1, row1Struct row1)
							throws java.lang.Exception {

						char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
						// support passing value (property: Field Separator) by 'context.fs' or
						// 'globalMap.get("fs")'.
						if (((String) ",").length() > 0) {
							fieldSeparator_tFileInputDelimited_1_ListType = ((String) ",").toCharArray();
						} else {
							throw new IllegalArgumentException("Field Separator must be assigned a char.");
						}
						if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																													// line
																													// when
																													// row
																													// separator
																													// is
																													// '\n'

							row1.Violation_Description___23 = null;

							row1.Violation_Points___23 = null;

							row1.Violation_Detail___23 = null;

							row1.Violation_Memo___23 = null;

							row1.Violation_Description___24 = null;

							row1.Violation_Points___24 = null;

							row1.Violation_Detail___24 = null;

							row1.Violation_Memo___24 = null;

							row1.Violation_Description___25 = null;

							row1.Violation_Points___25 = null;

							row1.Violation_Detail___25 = null;

							row1.Violation_Memo___25 = null;

							row1.Inspection_Month = null;

							row1.Inspection_Year = null;

							row1.end = null;

							row1.Address = null;

							row1.Latitude = null;

							row1.Longitude = null;

						} else {

							int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

							columnIndexWithD_tFileInputDelimited_1 = 100;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___23 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___23 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 101;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___23 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___23", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___23 = null;

								}

							} else {

								row1.Violation_Points___23 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 102;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___23 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___23 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 103;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___23 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___23 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 104;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___24 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___24 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 105;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___24 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___24", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___24 = null;

								}

							} else {

								row1.Violation_Points___24 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 106;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___24 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___24 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 107;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___24 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___24 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 108;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Description___25 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Description___25 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 109;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Violation_Points___25 = ParserUtils.parseTo_Double(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Violation_Points___25", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Violation_Points___25 = null;

								}

							} else {

								row1.Violation_Points___25 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 110;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Detail___25 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Detail___25 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 111;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Violation_Memo___25 = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Violation_Memo___25 = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 112;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Inspection_Month = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Inspection_Month = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 113;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Inspection_Year = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Inspection_Year = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 114;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.end = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.end = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 115;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								row1.Address = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

							} else {

								row1.Address = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 116;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Latitude = ParserUtils.parseTo_BigDecimal(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Latitude", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Latitude = null;

								}

							} else {

								row1.Latitude = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 117;

							if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

								if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
									try {

										row1.Longitude = ParserUtils.parseTo_BigDecimal(
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

									} catch (java.lang.Exception ex_tFileInputDelimited_1) {
										globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
												ex_tFileInputDelimited_1.getMessage());
										rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
												"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Longitude", "row1",
												rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
												ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
									}
								} else {

									row1.Longitude = null;

								}

							} else {

								row1.Longitude = null;

							}

						}

					}

					public void valueToConn(String[] rowtFileInputDelimited_1, row1Struct row1)
							throws java.lang.Exception {

						valueToConn_0(rowtFileInputDelimited_1, row1);

						valueToConn_1(rowtFileInputDelimited_1, row1);

					}

				}
				RowHelper_tFileInputDelimited_1 rowHelper_tFileInputDelimited_1 = new RowHelper_tFileInputDelimited_1();

				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 0;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;

				char fieldSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ",").length() > 0) {
					fieldSeparator_tFileInputDelimited_1 = ((String) ",").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_1 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */
						"C:/Users/siddh/Downloads/fin_dalas_staging_ready.xls"/**
																				 * End field
																				 * tFileInputDelimited_1:FILENAME
																				 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;

				try {

					String[] rowtFileInputDelimited_1 = null;
					int currentLinetFileInputDelimited_1 = 0;
					int outputLinetFileInputDelimited_1 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_1 = 0;
							if (footer_value_tFileInputDelimited_1 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_1,
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_1),
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						}

						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
							csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

						csvReadertFileInputDelimited_1.setQuoteChar('\"');

						csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						if (footer_tFileInputDelimited_1 > 0) {
							for (totalLinetFileInputDelimited_1 = 0; totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
							csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_1.readNext()) {

								totalLinetFileInputDelimited_1++;

							}
							int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1
									- footer_tFileInputDelimited_1 < 0 ? 0
											: totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1;
							if (lastLinetFileInputDelimited_1 > 0) {
								lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1
										? lastLinetFileInputDelimited_1
										: lastLineTemptFileInputDelimited_1;
							} else {
								lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
							}

							csvReadertFileInputDelimited_1.close();
							if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_1,
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_1),
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							}
							csvReadertFileInputDelimited_1.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
								csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

							csvReadertFileInputDelimited_1.setQuoteChar('\"');

							csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						}

						if (limittFileInputDelimited_1 != 0) {
							for (currentLinetFileInputDelimited_1 = 0; currentLinetFileInputDelimited_1 < 1; currentLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1 != null
							&& csvReadertFileInputDelimited_1.readNext()) {
						rowstate_tFileInputDelimited_1.reset();

						rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();

						currentLinetFileInputDelimited_1++;

						if (lastLinetFileInputDelimited_1 > -1
								&& currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
							break;
						}
						outputLinetFileInputDelimited_1++;
						if (limittFileInputDelimited_1 > 0
								&& outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							rowHelper_tFileInputDelimited_1.valueToConn(rowtFileInputDelimited_1, row1);

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - " + e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record " + (nb_line_tFileInputDelimited_1 + 1)
								+ ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						cLabel = "dallas_raw";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						cLabel = "dallas_raw";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row1", "tFileInputDelimited_1", "dallas_raw", "tFileInputDelimited", "tMap_1",
									"tMap_1", "tMap"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)

							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;
							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								load_stg_dallas = null;

// # Output table : 'load_stg_dallas'
								count_load_stg_dallas_tMap_1++;

								load_stg_dallas_tmp.Inspection_ID = row1.Inspection_ID;
								load_stg_dallas_tmp.Restaurant_Name = row1.Restaurant_Name;
								load_stg_dallas_tmp.Inspection_Type = row1.Inspection_Type;
								load_stg_dallas_tmp.Inspection_Date = row1.Inspection_Date.contains("-")
										? TalendDate.parseDate("MM-dd-yyyy", row1.Inspection_Date)
										: TalendDate.parseDate("MM/dd/yyyy", row1.Inspection_Date);
								load_stg_dallas_tmp.Inspection_Score = row1.Inspection_Score;
								load_stg_dallas_tmp.Street_Number = row1.Street_Number;
								load_stg_dallas_tmp.Street_Name = row1.Street_Name;
								load_stg_dallas_tmp.Street_Direction = row1.Street_Direction;
								load_stg_dallas_tmp.Street_Type = row1.Street_Type;
								load_stg_dallas_tmp.Street_Unit = row1.Street_Unit;
								load_stg_dallas_tmp.Street_Address = row1.Street_Address;
								load_stg_dallas_tmp.Zip_Code = row1.Zip_Code;
								load_stg_dallas_tmp.Violation_Points___1 = row1.Violation_Points___1 == null ? 0
										: row1.Violation_Points___1;
								load_stg_dallas_tmp.Violation_Points___2 = row1.Violation_Points___2 == null ? 0
										: row1.Violation_Points___2;
								load_stg_dallas_tmp.Violation_Points___3 = row1.Violation_Points___3 == null ? 0
										: row1.Violation_Points___3;
								load_stg_dallas_tmp.Violation_Points___4 = row1.Violation_Points___4 == null ? 0
										: row1.Violation_Points___4;
								load_stg_dallas_tmp.Violation_Points___5 = row1.Violation_Points___5 == null ? 0
										: row1.Violation_Points___5;
								load_stg_dallas_tmp.Violation_Points___6 = row1.Violation_Points___6 == null ? 0
										: row1.Violation_Points___6;
								load_stg_dallas_tmp.Violation_Points___7 = row1.Violation_Points___7 == null ? 0
										: row1.Violation_Points___7;
								load_stg_dallas_tmp.Violation_Points___8 = row1.Violation_Points___8 == null ? 0
										: row1.Violation_Points___8;
								load_stg_dallas_tmp.Violation_Points___9 = row1.Violation_Points___9 == null ? 0
										: row1.Violation_Points___9;
								load_stg_dallas_tmp.Violation_Points___10 = row1.Violation_Points___10 == null ? 0
										: row1.Violation_Points___10;
								load_stg_dallas_tmp.Violation_Points___11 = row1.Violation_Points___11 == null ? 0
										: row1.Violation_Points___11;
								load_stg_dallas_tmp.Violation_Points___12 = row1.Violation_Points___12 == null ? 0
										: row1.Violation_Points___12;
								load_stg_dallas_tmp.Violation_Points___13 = row1.Violation_Points___13 == null ? 0
										: row1.Violation_Points___13;
								load_stg_dallas_tmp.Violation_Points___14 = row1.Violation_Points___14 == null ? 0
										: row1.Violation_Points___14;
								load_stg_dallas_tmp.Violation_Points___15 = row1.Violation_Points___15 == null ? 0
										: row1.Violation_Points___15;
								load_stg_dallas_tmp.Violation_Points___16 = row1.Violation_Points___16 == null ? 0
										: row1.Violation_Points___16;
								load_stg_dallas_tmp.Violation_Points___17 = row1.Violation_Points___17 == null ? 0
										: row1.Violation_Points___17;
								load_stg_dallas_tmp.Violation_Points___18 = row1.Violation_Points___18 == null ? 0
										: row1.Violation_Points___18;
								load_stg_dallas_tmp.Violation_Points___19 = row1.Violation_Points___19 == null ? 0
										: row1.Violation_Points___19;
								load_stg_dallas_tmp.Violation_Points___20 = row1.Violation_Points___20 == null ? 0
										: row1.Violation_Points___20;
								load_stg_dallas_tmp.Violation_Points___21 = row1.Violation_Points___21 == null ? 0
										: row1.Violation_Points___21;
								load_stg_dallas_tmp.Violation_Points___22 = row1.Violation_Points___22 == null ? 0
										: row1.Violation_Points___22;
								load_stg_dallas_tmp.Violation_Points___23 = row1.Violation_Points___23 == null ? 0
										: row1.Violation_Points___23;
								load_stg_dallas_tmp.Violation_Points___24 = row1.Violation_Points___24 == null ? 0
										: row1.Violation_Points___24;
								load_stg_dallas_tmp.Violation_Points___25 = row1.Violation_Points___25 == null ? 0
										: row1.Violation_Points___25;
								load_stg_dallas_tmp.Inspection_Month = row1.Inspection_Month;
								load_stg_dallas_tmp.Inspection_Year = row1.Inspection_Year;
								load_stg_dallas_tmp.Address = row1.Address;
								load_stg_dallas_tmp.Latitude = row1.Latitude;
								load_stg_dallas_tmp.Longitude = row1.Longitude;
								load_stg_dallas_tmp.DI_CreateDate = TalendDate.getCurrentDate();
								load_stg_dallas_tmp.DI_WorkFlowFileNme = "Dallas Profile";
								load_stg_dallas = load_stg_dallas_tmp;
								log.debug("tMap_1 - Outputting the record " + count_load_stg_dallas_tMap_1
										+ " of the output table 'load_stg_dallas'.");

// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "load_stg_dallas"
							if (load_stg_dallas != null) {

								/**
								 * [tDBOutput_1 main ] start
								 */

								currentComponent = "tDBOutput_1";

								cLabel = "target_mysql";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "load_stg_dallas", "tMap_1", "tMap_1", "tMap", "tDBOutput_1", "target_mysql",
										"tMysqlOutput"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("load_stg_dallas - "
											+ (load_stg_dallas == null ? "" : load_stg_dallas.toLogString()));
								}

								whetherReject_tDBOutput_1 = false;
								if (load_stg_dallas.Inspection_ID == null) {
									pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(1, load_stg_dallas.Inspection_ID);
								}

								if (load_stg_dallas.Restaurant_Name == null) {
									pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(2, load_stg_dallas.Restaurant_Name);
								}

								if (load_stg_dallas.Inspection_Type == null) {
									pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(3, load_stg_dallas.Inspection_Type);
								}

								if (load_stg_dallas.Inspection_Date != null) {
									date_tDBOutput_1 = load_stg_dallas.Inspection_Date.getTime();
									if (date_tDBOutput_1 < year1_tDBOutput_1
											|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
										pstmt_tDBOutput_1.setString(4, "0000-00-00 00:00:00");
									} else {
										pstmt_tDBOutput_1.setTimestamp(4, new java.sql.Timestamp(date_tDBOutput_1));
									}
								} else {
									pstmt_tDBOutput_1.setNull(4, java.sql.Types.DATE);
								}

								if (load_stg_dallas.Inspection_Score == null) {
									pstmt_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(5, load_stg_dallas.Inspection_Score);
								}

								if (load_stg_dallas.Street_Number == null) {
									pstmt_tDBOutput_1.setNull(6, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(6, load_stg_dallas.Street_Number);
								}

								if (load_stg_dallas.Street_Name == null) {
									pstmt_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(7, load_stg_dallas.Street_Name);
								}

								if (load_stg_dallas.Street_Direction == null) {
									pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(8, load_stg_dallas.Street_Direction);
								}

								if (load_stg_dallas.Street_Type == null) {
									pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(9, load_stg_dallas.Street_Type);
								}

								if (load_stg_dallas.Street_Unit == null) {
									pstmt_tDBOutput_1.setNull(10, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(10, load_stg_dallas.Street_Unit);
								}

								if (load_stg_dallas.Street_Address == null) {
									pstmt_tDBOutput_1.setNull(11, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(11, load_stg_dallas.Street_Address);
								}

								if (load_stg_dallas.Zip_Code == null) {
									pstmt_tDBOutput_1.setNull(12, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(12, load_stg_dallas.Zip_Code);
								}

								if (load_stg_dallas.Violation_Points___1 == null) {
									pstmt_tDBOutput_1.setNull(13, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(13, load_stg_dallas.Violation_Points___1);
								}

								if (load_stg_dallas.Violation_Points___2 == null) {
									pstmt_tDBOutput_1.setNull(14, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(14, load_stg_dallas.Violation_Points___2);
								}

								if (load_stg_dallas.Violation_Points___3 == null) {
									pstmt_tDBOutput_1.setNull(15, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(15, load_stg_dallas.Violation_Points___3);
								}

								if (load_stg_dallas.Violation_Points___4 == null) {
									pstmt_tDBOutput_1.setNull(16, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(16, load_stg_dallas.Violation_Points___4);
								}

								if (load_stg_dallas.Violation_Points___5 == null) {
									pstmt_tDBOutput_1.setNull(17, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(17, load_stg_dallas.Violation_Points___5);
								}

								if (load_stg_dallas.Violation_Points___6 == null) {
									pstmt_tDBOutput_1.setNull(18, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(18, load_stg_dallas.Violation_Points___6);
								}

								if (load_stg_dallas.Violation_Points___7 == null) {
									pstmt_tDBOutput_1.setNull(19, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(19, load_stg_dallas.Violation_Points___7);
								}

								if (load_stg_dallas.Violation_Points___8 == null) {
									pstmt_tDBOutput_1.setNull(20, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(20, load_stg_dallas.Violation_Points___8);
								}

								if (load_stg_dallas.Violation_Points___9 == null) {
									pstmt_tDBOutput_1.setNull(21, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(21, load_stg_dallas.Violation_Points___9);
								}

								if (load_stg_dallas.Violation_Points___10 == null) {
									pstmt_tDBOutput_1.setNull(22, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(22, load_stg_dallas.Violation_Points___10);
								}

								if (load_stg_dallas.Violation_Points___11 == null) {
									pstmt_tDBOutput_1.setNull(23, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(23, load_stg_dallas.Violation_Points___11);
								}

								if (load_stg_dallas.Violation_Points___12 == null) {
									pstmt_tDBOutput_1.setNull(24, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(24, load_stg_dallas.Violation_Points___12);
								}

								if (load_stg_dallas.Violation_Points___13 == null) {
									pstmt_tDBOutput_1.setNull(25, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(25, load_stg_dallas.Violation_Points___13);
								}

								if (load_stg_dallas.Violation_Points___14 == null) {
									pstmt_tDBOutput_1.setNull(26, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(26, load_stg_dallas.Violation_Points___14);
								}

								if (load_stg_dallas.Violation_Points___15 == null) {
									pstmt_tDBOutput_1.setNull(27, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(27, load_stg_dallas.Violation_Points___15);
								}

								if (load_stg_dallas.Violation_Points___16 == null) {
									pstmt_tDBOutput_1.setNull(28, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(28, load_stg_dallas.Violation_Points___16);
								}

								if (load_stg_dallas.Violation_Points___17 == null) {
									pstmt_tDBOutput_1.setNull(29, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(29, load_stg_dallas.Violation_Points___17);
								}

								if (load_stg_dallas.Violation_Points___18 == null) {
									pstmt_tDBOutput_1.setNull(30, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(30, load_stg_dallas.Violation_Points___18);
								}

								if (load_stg_dallas.Violation_Points___19 == null) {
									pstmt_tDBOutput_1.setNull(31, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(31, load_stg_dallas.Violation_Points___19);
								}

								if (load_stg_dallas.Violation_Points___20 == null) {
									pstmt_tDBOutput_1.setNull(32, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(32, load_stg_dallas.Violation_Points___20);
								}

								if (load_stg_dallas.Violation_Points___21 == null) {
									pstmt_tDBOutput_1.setNull(33, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(33, load_stg_dallas.Violation_Points___21);
								}

								if (load_stg_dallas.Violation_Points___22 == null) {
									pstmt_tDBOutput_1.setNull(34, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(34, load_stg_dallas.Violation_Points___22);
								}

								if (load_stg_dallas.Violation_Points___23 == null) {
									pstmt_tDBOutput_1.setNull(35, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(35, load_stg_dallas.Violation_Points___23);
								}

								if (load_stg_dallas.Violation_Points___24 == null) {
									pstmt_tDBOutput_1.setNull(36, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(36, load_stg_dallas.Violation_Points___24);
								}

								if (load_stg_dallas.Violation_Points___25 == null) {
									pstmt_tDBOutput_1.setNull(37, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_1.setDouble(37, load_stg_dallas.Violation_Points___25);
								}

								if (load_stg_dallas.Inspection_Month == null) {
									pstmt_tDBOutput_1.setNull(38, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(38, load_stg_dallas.Inspection_Month);
								}

								if (load_stg_dallas.Inspection_Year == null) {
									pstmt_tDBOutput_1.setNull(39, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(39, load_stg_dallas.Inspection_Year);
								}

								if (load_stg_dallas.Address == null) {
									pstmt_tDBOutput_1.setNull(40, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(40, load_stg_dallas.Address);
								}

								pstmt_tDBOutput_1.setBigDecimal(41, load_stg_dallas.Latitude);

								pstmt_tDBOutput_1.setBigDecimal(42, load_stg_dallas.Longitude);

								if (load_stg_dallas.DI_CreateDate != null) {
									date_tDBOutput_1 = load_stg_dallas.DI_CreateDate.getTime();
									if (date_tDBOutput_1 < year1_tDBOutput_1
											|| date_tDBOutput_1 >= year10000_tDBOutput_1) {
										pstmt_tDBOutput_1.setString(43, "0000-00-00 00:00:00");
									} else {
										pstmt_tDBOutput_1.setTimestamp(43, new java.sql.Timestamp(date_tDBOutput_1));
									}
								} else {
									pstmt_tDBOutput_1.setNull(43, java.sql.Types.DATE);
								}

								if (load_stg_dallas.DI_WorkFlowFileNme == null) {
									pstmt_tDBOutput_1.setNull(44, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(44, load_stg_dallas.DI_WorkFlowFileNme);
								}

								pstmt_tDBOutput_1.addBatch();
								nb_line_tDBOutput_1++;

								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Adding the record ") + (nb_line_tDBOutput_1)
											+ (" to the ") + ("INSERT") + (" batch."));
								batchSizeCounter_tDBOutput_1++;
								if (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1) {
									try {
										int countSum_tDBOutput_1 = 0;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
										for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED
													? 0
													: 1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("The ") + ("INSERT")
													+ (" batch execution has succeeded."));
										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
									} catch (java.sql.BatchUpdateException e) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
										int countSum_tDBOutput_1 = 0;
										for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
										System.err.println(e.getMessage());
										log.error("tDBOutput_1 - " + (e.getMessage()));
									}

									batchSizeCounter_tDBOutput_1 = 0;
								}
								commitCounter_tDBOutput_1++;

								if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {

									try {
										int countSum_tDBOutput_1 = 0;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
										for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : 1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("The ") + ("INSERT")
													+ (" batch execution has succeeded."));
										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
									} catch (java.sql.BatchUpdateException e) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
										int countSum_tDBOutput_1 = 0;
										for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
										System.err.println(e.getMessage());
										log.error("tDBOutput_1 - " + (e.getMessage()));

									}
									if (rowsToCommitCount_tDBOutput_1 != 0) {
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Connection starting to commit ")
													+ (rowsToCommitCount_tDBOutput_1) + (" record(s)."));
									}
									conn_tDBOutput_1.commit();
									if (rowsToCommitCount_tDBOutput_1 != 0) {
										if (log.isDebugEnabled())
											log.debug("tDBOutput_1 - " + ("Connection commit has succeeded."));
										rowsToCommitCount_tDBOutput_1 = 0;
									}
									commitCounter_tDBOutput_1 = 0;
								}

								tos_count_tDBOutput_1++;

								/**
								 * [tDBOutput_1 main ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_1";

								cLabel = "target_mysql";

								/**
								 * [tDBOutput_1 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_end ] start
								 */

								currentComponent = "tDBOutput_1";

								cLabel = "target_mysql";

								/**
								 * [tDBOutput_1 process_data_end ] stop
								 */

							} // End of branch "load_stg_dallas"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						cLabel = "dallas_raw";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						cLabel = "dallas_raw";

						nb_line_tFileInputDelimited_1++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_1 != null) {
							csvReadertFileInputDelimited_1.close();
						}
					}
					if (csvReadertFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", nb_line_tFileInputDelimited_1);
					}

					log.info("tFileInputDelimited_1 - Retrieved records count: " + nb_line_tFileInputDelimited_1 + ".");

				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_1 - Written records count in the table 'load_stg_dallas': "
						+ count_load_stg_dallas_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tFileInputDelimited_1", "dallas_raw", "tFileInputDelimited", "tMap_1", "tMap_1", "tMap",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Done."));

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				cLabel = "target_mysql";

				try {
					if (batchSizeCounter_tDBOutput_1 != 0) {
						int countSum_tDBOutput_1 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_1 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					log.error("tDBOutput_1 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_1 = 0;

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");

				}

				resourceMap.put("statementClosed_tDBOutput_1", true);

				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_1) + (" record(s)."));
				}
				conn_tDBOutput_1.commit();
				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Closing the connection to the database."));
				conn_tDBOutput_1.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_1)
							+ (" record(s)."));

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "load_stg_dallas", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tDBOutput_1", "target_mysql", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_1 - " + ("Done."));

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				cLabel = "dallas_raw";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				cLabel = "target_mysql";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_1.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_1 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								log.error("tDBOutput_1 - " + (errorMessage_tDBOutput_1));
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "talendJobLog");
		org.slf4j.MDC.put("_subJobPid", "mQKNJD_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				ok_Hash.put("talendJobLog", false);
				start_Hash.put("talendJobLog", System.currentTimeMillis());

				currentComponent = "talendJobLog";

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				currentComponent = "talendJobLog";

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				currentComponent = "talendJobLog";

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final stg_chicago stg_chicagoClass = new stg_chicago();

		int exitCode = stg_chicagoClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'stg_chicago' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20231017_1026-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'stg_chicago' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_30lioNQeEe6gxbGc2yFGoQ");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-02-29T23:56:23.622177600Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = stg_chicago.class.getClassLoader()
					.getResourceAsStream("stg_dallas/stg_chicago_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = stg_chicago.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'stg_chicago' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : stg_chicago");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'stg_chicago' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 471584 characters generated by Talend Cloud Data Fabric on the 29 February
 * 2024 at 6:56:23 PM GMT-05:00
 ************************************************************************************************/