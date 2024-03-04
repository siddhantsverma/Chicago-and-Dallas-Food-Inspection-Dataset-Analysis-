
package stg_dallas.load_dallas_dim_0_1;

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
 * Job: load_dallas_dim Purpose: <br>
 * Description: <br>
 * 
 * @author verma.sidd@northeastern.edu
 * @version 8.0.1.20231017_1026-patch
 * @status
 */
public class load_dallas_dim implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "load_dallas_dim.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(load_dallas_dim.class);

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
	private final String jobName = "load_dallas_dim";
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
			"_WRcZcNgLEe6IueXiohRFmg", "0.1");
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
					load_dallas_dim.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(load_dallas_dim.this, new Object[] { e, currentComponent, globalMap });
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

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class inspection_factStruct implements routines.system.IPersistableRow<inspection_factStruct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_load_dallas_dim = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_load_dallas_dim = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int inspection_sk;

		public int getInspection_sk() {
			return this.inspection_sk;
		}

		public Boolean inspection_skIsNullable() {
			return false;
		}

		public Boolean inspection_skIsKey() {
			return true;
		}

		public Integer inspection_skLength() {
			return null;
		}

		public Integer inspection_skPrecision() {
			return null;
		}

		public String inspection_skDefault() {

			return null;

		}

		public String inspection_skComment() {

			return "";

		}

		public String inspection_skPattern() {

			return "";

		}

		public String inspection_skOriginalDbColumnName() {

			return "inspection_sk";

		}

		public int facility_sk;

		public int getFacility_sk() {
			return this.facility_sk;
		}

		public Boolean facility_skIsNullable() {
			return false;
		}

		public Boolean facility_skIsKey() {
			return false;
		}

		public Integer facility_skLength() {
			return null;
		}

		public Integer facility_skPrecision() {
			return null;
		}

		public String facility_skDefault() {

			return null;

		}

		public String facility_skComment() {

			return "";

		}

		public String facility_skPattern() {

			return "";

		}

		public String facility_skOriginalDbColumnName() {

			return "facility_sk";

		}

		public int date_sk;

		public int getDate_sk() {
			return this.date_sk;
		}

		public Boolean date_skIsNullable() {
			return false;
		}

		public Boolean date_skIsKey() {
			return false;
		}

		public Integer date_skLength() {
			return null;
		}

		public Integer date_skPrecision() {
			return null;
		}

		public String date_skDefault() {

			return null;

		}

		public String date_skComment() {

			return "";

		}

		public String date_skPattern() {

			return "";

		}

		public String date_skOriginalDbColumnName() {

			return "date_sk";

		}

		public int address_sk;

		public int getAddress_sk() {
			return this.address_sk;
		}

		public Boolean address_skIsNullable() {
			return false;
		}

		public Boolean address_skIsKey() {
			return false;
		}

		public Integer address_skLength() {
			return null;
		}

		public Integer address_skPrecision() {
			return null;
		}

		public String address_skDefault() {

			return null;

		}

		public String address_skComment() {

			return "";

		}

		public String address_skPattern() {

			return "";

		}

		public String address_skOriginalDbColumnName() {

			return "address_sk";

		}

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

			return "";

		}

		public String Inspection_IDOriginalDbColumnName() {

			return "Inspection_ID";

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

			return "";

		}

		public String Inspection_TypeOriginalDbColumnName() {

			return "Inspection_Type";

		}

		public Integer TotalViolationScore;

		public Integer getTotalViolationScore() {
			return this.TotalViolationScore;
		}

		public Boolean TotalViolationScoreIsNullable() {
			return true;
		}

		public Boolean TotalViolationScoreIsKey() {
			return false;
		}

		public Integer TotalViolationScoreLength() {
			return null;
		}

		public Integer TotalViolationScorePrecision() {
			return null;
		}

		public String TotalViolationScoreDefault() {

			return null;

		}

		public String TotalViolationScoreComment() {

			return "";

		}

		public String TotalViolationScorePattern() {

			return "";

		}

		public String TotalViolationScoreOriginalDbColumnName() {

			return "TotalViolationScore";

		}

		public String Inspection_Result;

		public String getInspection_Result() {
			return this.Inspection_Result;
		}

		public Boolean Inspection_ResultIsNullable() {
			return true;
		}

		public Boolean Inspection_ResultIsKey() {
			return false;
		}

		public Integer Inspection_ResultLength() {
			return 500;
		}

		public Integer Inspection_ResultPrecision() {
			return null;
		}

		public String Inspection_ResultDefault() {

			return null;

		}

		public String Inspection_ResultComment() {

			return "";

		}

		public String Inspection_ResultPattern() {

			return "";

		}

		public String Inspection_ResultOriginalDbColumnName() {

			return "Inspection_Result";

		}

		public String DI_WorkflowFileName;

		public String getDI_WorkflowFileName() {
			return this.DI_WorkflowFileName;
		}

		public Boolean DI_WorkflowFileNameIsNullable() {
			return true;
		}

		public Boolean DI_WorkflowFileNameIsKey() {
			return false;
		}

		public Integer DI_WorkflowFileNameLength() {
			return 200;
		}

		public Integer DI_WorkflowFileNamePrecision() {
			return null;
		}

		public String DI_WorkflowFileNameDefault() {

			return null;

		}

		public String DI_WorkflowFileNameComment() {

			return "";

		}

		public String DI_WorkflowFileNamePattern() {

			return "";

		}

		public String DI_WorkflowFileNameOriginalDbColumnName() {

			return "DI_WorkflowFileName";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.inspection_sk;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final inspection_factStruct other = (inspection_factStruct) obj;

			if (this.inspection_sk != other.inspection_sk)
				return false;

			return true;
		}

		public void copyDataTo(inspection_factStruct other) {

			other.inspection_sk = this.inspection_sk;
			other.facility_sk = this.facility_sk;
			other.date_sk = this.date_sk;
			other.address_sk = this.address_sk;
			other.Inspection_ID = this.Inspection_ID;
			other.Inspection_Type = this.Inspection_Type;
			other.TotalViolationScore = this.TotalViolationScore;
			other.Inspection_Result = this.Inspection_Result;
			other.DI_WorkflowFileName = this.DI_WorkflowFileName;

		}

		public void copyKeysDataTo(inspection_factStruct other) {

			other.inspection_sk = this.inspection_sk;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.inspection_sk = dis.readInt();

					this.facility_sk = dis.readInt();

					this.date_sk = dis.readInt();

					this.address_sk = dis.readInt();

					this.Inspection_ID = readString(dis);

					this.Inspection_Type = readString(dis);

					this.TotalViolationScore = readInteger(dis);

					this.Inspection_Result = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.inspection_sk = dis.readInt();

					this.facility_sk = dis.readInt();

					this.date_sk = dis.readInt();

					this.address_sk = dis.readInt();

					this.Inspection_ID = readString(dis);

					this.Inspection_Type = readString(dis);

					this.TotalViolationScore = readInteger(dis);

					this.Inspection_Result = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.inspection_sk);

				// int

				dos.writeInt(this.facility_sk);

				// int

				dos.writeInt(this.date_sk);

				// int

				dos.writeInt(this.address_sk);

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// Integer

				writeInteger(this.TotalViolationScore, dos);

				// String

				writeString(this.Inspection_Result, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.inspection_sk);

				// int

				dos.writeInt(this.facility_sk);

				// int

				dos.writeInt(this.date_sk);

				// int

				dos.writeInt(this.address_sk);

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Inspection_Type, dos);

				// Integer

				writeInteger(this.TotalViolationScore, dos);

				// String

				writeString(this.Inspection_Result, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("inspection_sk=" + String.valueOf(inspection_sk));
			sb.append(",facility_sk=" + String.valueOf(facility_sk));
			sb.append(",date_sk=" + String.valueOf(date_sk));
			sb.append(",address_sk=" + String.valueOf(address_sk));
			sb.append(",Inspection_ID=" + Inspection_ID);
			sb.append(",Inspection_Type=" + Inspection_Type);
			sb.append(",TotalViolationScore=" + String.valueOf(TotalViolationScore));
			sb.append(",Inspection_Result=" + Inspection_Result);
			sb.append(",DI_WorkflowFileName=" + DI_WorkflowFileName);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(inspection_sk);

			sb.append("|");

			sb.append(facility_sk);

			sb.append("|");

			sb.append(date_sk);

			sb.append("|");

			sb.append(address_sk);

			sb.append("|");

			if (Inspection_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_ID);
			}

			sb.append("|");

			if (Inspection_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Type);
			}

			sb.append("|");

			if (TotalViolationScore == null) {
				sb.append("<null>");
			} else {
				sb.append(TotalViolationScore);
			}

			sb.append("|");

			if (Inspection_Result == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Result);
			}

			sb.append("|");

			if (DI_WorkflowFileName == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_WorkflowFileName);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(inspection_factStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.inspection_sk, other.inspection_sk);
			if (returnValue != 0) {
				return returnValue;
			}

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

	public static class date_dimensionStruct implements routines.system.IPersistableRow<date_dimensionStruct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_load_dallas_dim = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_load_dallas_dim = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int date_sk;

		public int getDate_sk() {
			return this.date_sk;
		}

		public Boolean date_skIsNullable() {
			return false;
		}

		public Boolean date_skIsKey() {
			return true;
		}

		public Integer date_skLength() {
			return null;
		}

		public Integer date_skPrecision() {
			return null;
		}

		public String date_skDefault() {

			return "";

		}

		public String date_skComment() {

			return "";

		}

		public String date_skPattern() {

			return "";

		}

		public String date_skOriginalDbColumnName() {

			return "date_sk";

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
			return 19;
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

			return "yyyy-MM-dd";

		}

		public String Inspection_DateOriginalDbColumnName() {

			return "Inspection_Date";

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
			return 20;
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

			return "";

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
			return 20;
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

			return "";

		}

		public String Inspection_YearOriginalDbColumnName() {

			return "Inspection_Year";

		}

		public String Inspection_Day;

		public String getInspection_Day() {
			return this.Inspection_Day;
		}

		public Boolean Inspection_DayIsNullable() {
			return true;
		}

		public Boolean Inspection_DayIsKey() {
			return false;
		}

		public Integer Inspection_DayLength() {
			return 20;
		}

		public Integer Inspection_DayPrecision() {
			return null;
		}

		public String Inspection_DayDefault() {

			return null;

		}

		public String Inspection_DayComment() {

			return "";

		}

		public String Inspection_DayPattern() {

			return "";

		}

		public String Inspection_DayOriginalDbColumnName() {

			return "Inspection_Day";

		}

		public String DI_WorkflowFileName;

		public String getDI_WorkflowFileName() {
			return this.DI_WorkflowFileName;
		}

		public Boolean DI_WorkflowFileNameIsNullable() {
			return true;
		}

		public Boolean DI_WorkflowFileNameIsKey() {
			return false;
		}

		public Integer DI_WorkflowFileNameLength() {
			return 300;
		}

		public Integer DI_WorkflowFileNamePrecision() {
			return null;
		}

		public String DI_WorkflowFileNameDefault() {

			return null;

		}

		public String DI_WorkflowFileNameComment() {

			return "";

		}

		public String DI_WorkflowFileNamePattern() {

			return "";

		}

		public String DI_WorkflowFileNameOriginalDbColumnName() {

			return "DI_WorkflowFileName";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.date_sk;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final date_dimensionStruct other = (date_dimensionStruct) obj;

			if (this.date_sk != other.date_sk)
				return false;

			return true;
		}

		public void copyDataTo(date_dimensionStruct other) {

			other.date_sk = this.date_sk;
			other.Inspection_Date = this.Inspection_Date;
			other.Inspection_Month = this.Inspection_Month;
			other.Inspection_Year = this.Inspection_Year;
			other.Inspection_Day = this.Inspection_Day;
			other.DI_WorkflowFileName = this.DI_WorkflowFileName;

		}

		public void copyKeysDataTo(date_dimensionStruct other) {

			other.date_sk = this.date_sk;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.date_sk = dis.readInt();

					this.Inspection_Date = readDate(dis);

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.Inspection_Day = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.date_sk = dis.readInt();

					this.Inspection_Date = readDate(dis);

					this.Inspection_Month = readString(dis);

					this.Inspection_Year = readString(dis);

					this.Inspection_Day = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.date_sk);

				// java.util.Date

				writeDate(this.Inspection_Date, dos);

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.Inspection_Day, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.date_sk);

				// java.util.Date

				writeDate(this.Inspection_Date, dos);

				// String

				writeString(this.Inspection_Month, dos);

				// String

				writeString(this.Inspection_Year, dos);

				// String

				writeString(this.Inspection_Day, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date_sk=" + String.valueOf(date_sk));
			sb.append(",Inspection_Date=" + String.valueOf(Inspection_Date));
			sb.append(",Inspection_Month=" + Inspection_Month);
			sb.append(",Inspection_Year=" + Inspection_Year);
			sb.append(",Inspection_Day=" + Inspection_Day);
			sb.append(",DI_WorkflowFileName=" + DI_WorkflowFileName);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(date_sk);

			sb.append("|");

			if (Inspection_Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Date);
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

			if (Inspection_Day == null) {
				sb.append("<null>");
			} else {
				sb.append(Inspection_Day);
			}

			sb.append("|");

			if (DI_WorkflowFileName == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_WorkflowFileName);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(date_dimensionStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date_sk, other.date_sk);
			if (returnValue != 0) {
				return returnValue;
			}

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

	public static class address_dimensionStruct implements routines.system.IPersistableRow<address_dimensionStruct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_load_dallas_dim = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_load_dallas_dim = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int address_sk;

		public int getAddress_sk() {
			return this.address_sk;
		}

		public Boolean address_skIsNullable() {
			return false;
		}

		public Boolean address_skIsKey() {
			return true;
		}

		public Integer address_skLength() {
			return null;
		}

		public Integer address_skPrecision() {
			return null;
		}

		public String address_skDefault() {

			return "";

		}

		public String address_skComment() {

			return "";

		}

		public String address_skPattern() {

			return "";

		}

		public String address_skOriginalDbColumnName() {

			return "address_sk";

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

			return "";

		}

		public String AddressOriginalDbColumnName() {

			return "Address";

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

			return "";

		}

		public String Zip_CodeOriginalDbColumnName() {

			return "Zip_Code";

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

			return "";

		}

		public String LatitudeComment() {

			return "";

		}

		public String LatitudePattern() {

			return "";

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

			return "";

		}

		public String LongitudeComment() {

			return "";

		}

		public String LongitudePattern() {

			return "";

		}

		public String LongitudeOriginalDbColumnName() {

			return "Longitude";

		}

		public String DI_WorkflowFileName;

		public String getDI_WorkflowFileName() {
			return this.DI_WorkflowFileName;
		}

		public Boolean DI_WorkflowFileNameIsNullable() {
			return true;
		}

		public Boolean DI_WorkflowFileNameIsKey() {
			return false;
		}

		public Integer DI_WorkflowFileNameLength() {
			return 200;
		}

		public Integer DI_WorkflowFileNamePrecision() {
			return null;
		}

		public String DI_WorkflowFileNameDefault() {

			return null;

		}

		public String DI_WorkflowFileNameComment() {

			return "";

		}

		public String DI_WorkflowFileNamePattern() {

			return "";

		}

		public String DI_WorkflowFileNameOriginalDbColumnName() {

			return "DI_WorkflowFileName";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.address_sk;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final address_dimensionStruct other = (address_dimensionStruct) obj;

			if (this.address_sk != other.address_sk)
				return false;

			return true;
		}

		public void copyDataTo(address_dimensionStruct other) {

			other.address_sk = this.address_sk;
			other.Address = this.Address;
			other.Zip_Code = this.Zip_Code;
			other.Latitude = this.Latitude;
			other.Longitude = this.Longitude;
			other.DI_WorkflowFileName = this.DI_WorkflowFileName;

		}

		public void copyKeysDataTo(address_dimensionStruct other) {

			other.address_sk = this.address_sk;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.address_sk = dis.readInt();

					this.Address = readString(dis);

					this.Zip_Code = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.address_sk = dis.readInt();

					this.Address = readString(dis);

					this.Zip_Code = readString(dis);

					this.Latitude = (BigDecimal) dis.readObject();

					this.Longitude = (BigDecimal) dis.readObject();

					this.DI_WorkflowFileName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.address_sk);

				// String

				writeString(this.Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// BigDecimal

				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.writeObject(this.Longitude);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.address_sk);

				// String

				writeString(this.Address, dos);

				// String

				writeString(this.Zip_Code, dos);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Latitude);

				// BigDecimal

				dos.clearInstanceCache();
				dos.writeObject(this.Longitude);

				// String

				writeString(this.DI_WorkflowFileName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("address_sk=" + String.valueOf(address_sk));
			sb.append(",Address=" + Address);
			sb.append(",Zip_Code=" + Zip_Code);
			sb.append(",Latitude=" + String.valueOf(Latitude));
			sb.append(",Longitude=" + String.valueOf(Longitude));
			sb.append(",DI_WorkflowFileName=" + DI_WorkflowFileName);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(address_sk);

			sb.append("|");

			if (Address == null) {
				sb.append("<null>");
			} else {
				sb.append(Address);
			}

			sb.append("|");

			if (Zip_Code == null) {
				sb.append("<null>");
			} else {
				sb.append(Zip_Code);
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

			if (DI_WorkflowFileName == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_WorkflowFileName);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(address_dimensionStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.address_sk, other.address_sk);
			if (returnValue != 0) {
				return returnValue;
			}

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

	public static class facility_dimensionStruct implements routines.system.IPersistableRow<facility_dimensionStruct> {
		final static byte[] commonByteArrayLock_STG_DALLAS_load_dallas_dim = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_load_dallas_dim = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int facility_sk;

		public int getFacility_sk() {
			return this.facility_sk;
		}

		public Boolean facility_skIsNullable() {
			return false;
		}

		public Boolean facility_skIsKey() {
			return true;
		}

		public Integer facility_skLength() {
			return null;
		}

		public Integer facility_skPrecision() {
			return null;
		}

		public String facility_skDefault() {

			return "";

		}

		public String facility_skComment() {

			return "";

		}

		public String facility_skPattern() {

			return "";

		}

		public String facility_skOriginalDbColumnName() {

			return "facility_sk";

		}

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

			return "";

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

			return "";

		}

		public String Restaurant_NameOriginalDbColumnName() {

			return "Restaurant_Name";

		}

		public String DI_WorkflowFileName;

		public String getDI_WorkflowFileName() {
			return this.DI_WorkflowFileName;
		}

		public Boolean DI_WorkflowFileNameIsNullable() {
			return true;
		}

		public Boolean DI_WorkflowFileNameIsKey() {
			return false;
		}

		public Integer DI_WorkflowFileNameLength() {
			return 200;
		}

		public Integer DI_WorkflowFileNamePrecision() {
			return null;
		}

		public String DI_WorkflowFileNameDefault() {

			return null;

		}

		public String DI_WorkflowFileNameComment() {

			return "";

		}

		public String DI_WorkflowFileNamePattern() {

			return "";

		}

		public String DI_WorkflowFileNameOriginalDbColumnName() {

			return "DI_WorkflowFileName";

		}

		public String Facility_Type;

		public String getFacility_Type() {
			return this.Facility_Type;
		}

		public Boolean Facility_TypeIsNullable() {
			return true;
		}

		public Boolean Facility_TypeIsKey() {
			return false;
		}

		public Integer Facility_TypeLength() {
			return 200;
		}

		public Integer Facility_TypePrecision() {
			return null;
		}

		public String Facility_TypeDefault() {

			return null;

		}

		public String Facility_TypeComment() {

			return "";

		}

		public String Facility_TypePattern() {

			return "";

		}

		public String Facility_TypeOriginalDbColumnName() {

			return "Facility_Type";

		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.facility_sk;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final facility_dimensionStruct other = (facility_dimensionStruct) obj;

			if (this.facility_sk != other.facility_sk)
				return false;

			return true;
		}

		public void copyDataTo(facility_dimensionStruct other) {

			other.facility_sk = this.facility_sk;
			other.Inspection_ID = this.Inspection_ID;
			other.Restaurant_Name = this.Restaurant_Name;
			other.DI_WorkflowFileName = this.DI_WorkflowFileName;
			other.Facility_Type = this.Facility_Type;

		}

		public void copyKeysDataTo(facility_dimensionStruct other) {

			other.facility_sk = this.facility_sk;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.facility_sk = dis.readInt();

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

					this.Facility_Type = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

				try {

					int length = 0;

					this.facility_sk = dis.readInt();

					this.Inspection_ID = readString(dis);

					this.Restaurant_Name = readString(dis);

					this.DI_WorkflowFileName = readString(dis);

					this.Facility_Type = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.facility_sk);

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

				// String

				writeString(this.Facility_Type, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.facility_sk);

				// String

				writeString(this.Inspection_ID, dos);

				// String

				writeString(this.Restaurant_Name, dos);

				// String

				writeString(this.DI_WorkflowFileName, dos);

				// String

				writeString(this.Facility_Type, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("facility_sk=" + String.valueOf(facility_sk));
			sb.append(",Inspection_ID=" + Inspection_ID);
			sb.append(",Restaurant_Name=" + Restaurant_Name);
			sb.append(",DI_WorkflowFileName=" + DI_WorkflowFileName);
			sb.append(",Facility_Type=" + Facility_Type);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			sb.append(facility_sk);

			sb.append("|");

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

			if (DI_WorkflowFileName == null) {
				sb.append("<null>");
			} else {
				sb.append(DI_WorkflowFileName);
			}

			sb.append("|");

			if (Facility_Type == null) {
				sb.append("<null>");
			} else {
				sb.append(Facility_Type);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(facility_dimensionStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.facility_sk, other.facility_sk);
			if (returnValue != 0) {
				return returnValue;
			}

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
		final static byte[] commonByteArrayLock_STG_DALLAS_load_dallas_dim = new byte[0];
		static byte[] commonByteArray_STG_DALLAS_load_dallas_dim = new byte[0];

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

			return "";

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

			return "";

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

			return "";

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
			return 19;
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

			return "yyyy-MM-dd";

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
			return 10;
		}

		public Integer Inspection_ScorePrecision() {
			return 0;
		}

		public String Inspection_ScoreDefault() {

			return "";

		}

		public String Inspection_ScoreComment() {

			return "";

		}

		public String Inspection_ScorePattern() {

			return "";

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
			return 10;
		}

		public Integer Street_NumberPrecision() {
			return 0;
		}

		public String Street_NumberDefault() {

			return "";

		}

		public String Street_NumberComment() {

			return "";

		}

		public String Street_NumberPattern() {

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

		}

		public String Violation_Points___1Comment() {

			return "";

		}

		public String Violation_Points___1Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___2Comment() {

			return "";

		}

		public String Violation_Points___2Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___3Comment() {

			return "";

		}

		public String Violation_Points___3Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___4Comment() {

			return "";

		}

		public String Violation_Points___4Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___5Comment() {

			return "";

		}

		public String Violation_Points___5Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___6Comment() {

			return "";

		}

		public String Violation_Points___6Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___7Comment() {

			return "";

		}

		public String Violation_Points___7Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___8Comment() {

			return "";

		}

		public String Violation_Points___8Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___9Comment() {

			return "";

		}

		public String Violation_Points___9Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___10Comment() {

			return "";

		}

		public String Violation_Points___10Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___11Comment() {

			return "";

		}

		public String Violation_Points___11Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___12Comment() {

			return "";

		}

		public String Violation_Points___12Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___13Comment() {

			return "";

		}

		public String Violation_Points___13Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___14Comment() {

			return "";

		}

		public String Violation_Points___14Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___15Comment() {

			return "";

		}

		public String Violation_Points___15Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___16Comment() {

			return "";

		}

		public String Violation_Points___16Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___17Comment() {

			return "";

		}

		public String Violation_Points___17Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___18Comment() {

			return "";

		}

		public String Violation_Points___18Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___19Comment() {

			return "";

		}

		public String Violation_Points___19Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___20Comment() {

			return "";

		}

		public String Violation_Points___20Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___21Comment() {

			return "";

		}

		public String Violation_Points___21Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___22Comment() {

			return "";

		}

		public String Violation_Points___22Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___23Comment() {

			return "";

		}

		public String Violation_Points___23Pattern() {

			return "";

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

			return "";

		}

		public String Violation_Points___24Comment() {

			return "";

		}

		public String Violation_Points___24Pattern() {

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

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

			return "";

		}

		public String LatitudeComment() {

			return "";

		}

		public String LatitudePattern() {

			return "";

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

			return "";

		}

		public String LongitudeComment() {

			return "";

		}

		public String LongitudePattern() {

			return "";

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
			return 19;
		}

		public Integer DI_CreateDatePrecision() {
			return 0;
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
			return 0;
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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
				if (length > commonByteArray_STG_DALLAS_load_dallas_dim.length) {
					if (length < 1024 && commonByteArray_STG_DALLAS_load_dallas_dim.length == 0) {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[1024];
					} else {
						commonByteArray_STG_DALLAS_load_dallas_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length);
				strReturn = new String(commonByteArray_STG_DALLAS_load_dallas_dim, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

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

			synchronized (commonByteArrayLock_STG_DALLAS_load_dallas_dim) {

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

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tDBInput_1");
		org.slf4j.MDC.put("_subJobPid", "PCiWRb_" + subJobPidCounter.getAndIncrement());

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
				inspection_factStruct inspection_fact = new inspection_factStruct();
				date_dimensionStruct date_dimension = new date_dimensionStruct();
				address_dimensionStruct address_dimension = new address_dimensionStruct();
				facility_dimensionStruct facility_dimension = new facility_dimensionStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				cLabel = "target_mysql";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "inspection_fact");

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
									"enc:routine.encryption.key.v1:1gC0aLjVlGkuQ9dQXZvTOJg27xNvEHCSNAMPI+I7qhzxNm/E5S9MYA==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_1.append(" | ");
							log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"stg_dallas_inspection_fact_table\"");
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

				String tableName_tDBOutput_1 = "stg_dallas_inspection_fact_table";
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
						"enc:routine.encryption.key.v1:WMcaiByxopDVfPQ1EHWB9ViBGvSEpRkpaEYbUEgfu+8wHM8r7dx2hw==");

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
					if (table_tDBOutput_1.equalsIgnoreCase("stg_dallas_inspection_fact_table")) {
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
							+ "`(`inspection_sk` INT(0)   not null ,`facility_sk` INT(0)   not null ,`date_sk` INT(0)   not null ,`address_sk` INT(0)   not null ,`Inspection_ID` VARCHAR(10)  ,`Inspection_Type` VARCHAR(9)  ,`TotalViolationScore` INT(0)  ,`Inspection_Result` VARCHAR(500)  ,`DI_WorkflowFileName` VARCHAR(200)  ,primary key(`inspection_sk`))");
					if (log.isDebugEnabled())
						log.debug("tDBOutput_1 - " + ("Create") + (" table '") + (tableName_tDBOutput_1)
								+ ("' has succeeded."));
				}

				String insert_tDBOutput_1 = "INSERT INTO `" + "stg_dallas_inspection_fact_table"
						+ "` (`inspection_sk`,`facility_sk`,`date_sk`,`address_sk`,`Inspection_ID`,`Inspection_Type`,`TotalViolationScore`,`Inspection_Result`,`DI_WorkflowFileName`) VALUES (?,?,?,?,?,?,?,?,?)";

				int batchSize_tDBOutput_1 = 100;
				int batchSizeCounter_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				cLabel = "target_mysql";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "date_dimension");

				int tos_count_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_2 = new StringBuilder();
							log4jParamters_tDBOutput_2.append("Parameters:");
							log4jParamters_tDBOutput_2.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DBNAME" + " = " + "\"midterm\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USER" + " = " + "\"root\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:158SNHm9WuSc2TEIEHIdYjNG/gk+kiPI+io/DobKbzv5LIB4xPPesQ==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"stg_dallas_date_dimension_table\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "DROP_IF_EXISTS_AND_CREATE");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_2.append(" | ");
							log4jParamters_tDBOutput_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_2 - " + (log4jParamters_tDBOutput_2));
						}
					}
					new BytesLimit65535_tDBOutput_2().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_2", "target_mysql", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				String tableName_tDBOutput_2 = "stg_dallas_date_dimension_table";
				boolean whetherReject_tDBOutput_2 = false;

				java.util.Calendar calendar_tDBOutput_2 = java.util.Calendar.getInstance();
				calendar_tDBOutput_2.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				calendar_tDBOutput_2.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				long date_tDBOutput_2;

				java.sql.Connection conn_tDBOutput_2 = null;

				String properties_tDBOutput_2 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_2 == null || properties_tDBOutput_2.trim().length() == 0) {
					properties_tDBOutput_2 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_2.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_2 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_2.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_2 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_2 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "midterm" + "?"
						+ properties_tDBOutput_2;

				String driverClass_tDBOutput_2 = "com.mysql.cj.jdbc.Driver";

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Driver ClassName: ") + (driverClass_tDBOutput_2) + ("."));
				String dbUser_tDBOutput_2 = "root";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:woSckziGHaPu4lwg1cVZZvNfhTSC0VK/9BOsugBafCvZD9YyQLbVug==");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;
				java.lang.Class.forName(driverClass_tDBOutput_2);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection attempts to '") + (url_tDBOutput_2)
							+ ("' with the username '") + (dbUser_tDBOutput_2) + ("'."));
				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection to '") + (url_tDBOutput_2) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);

				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_2.getAutoCommit()) + ("'."));

				int count_tDBOutput_2 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_2 = conn_tDBOutput_2.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_2 = dbMetaData_tDBOutput_2.getTables("midterm", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_2 = false;
				while (rsTable_tDBOutput_2.next()) {
					String table_tDBOutput_2 = rsTable_tDBOutput_2.getString("TABLE_NAME");
					if (table_tDBOutput_2.equalsIgnoreCase("stg_dallas_date_dimension_table")) {
						whetherExist_tDBOutput_2 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_2) {
					try (java.sql.Statement stmtDrop_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
						if (log.isDebugEnabled())
							log.debug(
									"tDBOutput_2 - " + ("Dropping") + (" table '") + (tableName_tDBOutput_2) + ("'."));
						stmtDrop_tDBOutput_2.execute("DROP TABLE `" + tableName_tDBOutput_2 + "`");
						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Drop") + (" table '") + (tableName_tDBOutput_2)
									+ ("' has succeeded."));
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Creating") + (" table '") + (tableName_tDBOutput_2) + ("'."));
					stmtCreate_tDBOutput_2.execute("CREATE TABLE `" + tableName_tDBOutput_2
							+ "`(`date_sk` INT(0)   not null ,`Inspection_Date` DATETIME ,`Inspection_Month` VARCHAR(20)  ,`Inspection_Year` VARCHAR(20)  ,`Inspection_Day` VARCHAR(20)  ,`DI_WorkflowFileName` VARCHAR(300)  ,primary key(`date_sk`))");
					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Create") + (" table '") + (tableName_tDBOutput_2)
								+ ("' has succeeded."));
				}

				String insert_tDBOutput_2 = "INSERT INTO `" + "stg_dallas_date_dimension_table"
						+ "` (`date_sk`,`Inspection_Date`,`Inspection_Month`,`Inspection_Year`,`Inspection_Day`,`DI_WorkflowFileName`) VALUES (?,?,?,?,?,?)";

				int batchSize_tDBOutput_2 = 100;
				int batchSizeCounter_tDBOutput_2 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tDBOutput_3 begin ] start
				 */

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				cLabel = "target_mysql";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "address_dimension");

				int tos_count_tDBOutput_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_3 = new StringBuilder();
							log4jParamters_tDBOutput_3.append("Parameters:");
							log4jParamters_tDBOutput_3.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DBNAME" + " = " + "\"midterm\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USER" + " = " + "\"root\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:IrMb0nGoviM23JSsrzg4TcxQEuj6+MM4Bv2xOD9nn4T2D+6Pxi7gog==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3
									.append("TABLE" + " = " + "\"stg_dallas_address_dimension_table\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("TABLE_ACTION" + " = " + "DROP_IF_EXISTS_AND_CREATE");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_3.append(" | ");
							log4jParamters_tDBOutput_3.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_3 - " + (log4jParamters_tDBOutput_3));
						}
					}
					new BytesLimit65535_tDBOutput_3().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_3", "target_mysql", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_3 = 0;
				int nb_line_update_tDBOutput_3 = 0;
				int nb_line_inserted_tDBOutput_3 = 0;
				int nb_line_deleted_tDBOutput_3 = 0;
				int nb_line_rejected_tDBOutput_3 = 0;

				int deletedCount_tDBOutput_3 = 0;
				int updatedCount_tDBOutput_3 = 0;
				int insertedCount_tDBOutput_3 = 0;
				int rowsToCommitCount_tDBOutput_3 = 0;
				int rejectedCount_tDBOutput_3 = 0;

				String tableName_tDBOutput_3 = "stg_dallas_address_dimension_table";
				boolean whetherReject_tDBOutput_3 = false;

				java.util.Calendar calendar_tDBOutput_3 = java.util.Calendar.getInstance();
				calendar_tDBOutput_3.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				calendar_tDBOutput_3.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				long date_tDBOutput_3;

				java.sql.Connection conn_tDBOutput_3 = null;

				String properties_tDBOutput_3 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_3 == null || properties_tDBOutput_3.trim().length() == 0) {
					properties_tDBOutput_3 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_3.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_3 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_3.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_3 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_3 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "midterm" + "?"
						+ properties_tDBOutput_3;

				String driverClass_tDBOutput_3 = "com.mysql.cj.jdbc.Driver";

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Driver ClassName: ") + (driverClass_tDBOutput_3) + ("."));
				String dbUser_tDBOutput_3 = "root";

				final String decryptedPassword_tDBOutput_3 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:8oqGA0K6zzVjYneF3EGeaZuvhIH/UNjTAv2qJdfKfdZ0bDW9MNAYQA==");

				String dbPwd_tDBOutput_3 = decryptedPassword_tDBOutput_3;
				java.lang.Class.forName(driverClass_tDBOutput_3);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Connection attempts to '") + (url_tDBOutput_3)
							+ ("' with the username '") + (dbUser_tDBOutput_3) + ("'."));
				conn_tDBOutput_3 = java.sql.DriverManager.getConnection(url_tDBOutput_3, dbUser_tDBOutput_3,
						dbPwd_tDBOutput_3);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Connection to '") + (url_tDBOutput_3) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_3", conn_tDBOutput_3);

				conn_tDBOutput_3.setAutoCommit(false);
				int commitEvery_tDBOutput_3 = 10000;
				int commitCounter_tDBOutput_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_3.getAutoCommit()) + ("'."));

				int count_tDBOutput_3 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_3 = conn_tDBOutput_3.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_3 = dbMetaData_tDBOutput_3.getTables("midterm", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_3 = false;
				while (rsTable_tDBOutput_3.next()) {
					String table_tDBOutput_3 = rsTable_tDBOutput_3.getString("TABLE_NAME");
					if (table_tDBOutput_3.equalsIgnoreCase("stg_dallas_address_dimension_table")) {
						whetherExist_tDBOutput_3 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_3) {
					try (java.sql.Statement stmtDrop_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
						if (log.isDebugEnabled())
							log.debug(
									"tDBOutput_3 - " + ("Dropping") + (" table '") + (tableName_tDBOutput_3) + ("'."));
						stmtDrop_tDBOutput_3.execute("DROP TABLE `" + tableName_tDBOutput_3 + "`");
						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("Drop") + (" table '") + (tableName_tDBOutput_3)
									+ ("' has succeeded."));
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
					if (log.isDebugEnabled())
						log.debug("tDBOutput_3 - " + ("Creating") + (" table '") + (tableName_tDBOutput_3) + ("'."));
					stmtCreate_tDBOutput_3.execute("CREATE TABLE `" + tableName_tDBOutput_3
							+ "`(`address_sk` INT(0)   not null ,`Address` VARCHAR(250)  ,`Zip_Code` VARCHAR(10)  ,`Latitude` DECIMAL(65,8)  ,`Longitude` DECIMAL(65,8)  ,`DI_WorkflowFileName` VARCHAR(200)  ,primary key(`address_sk`))");
					if (log.isDebugEnabled())
						log.debug("tDBOutput_3 - " + ("Create") + (" table '") + (tableName_tDBOutput_3)
								+ ("' has succeeded."));
				}

				String insert_tDBOutput_3 = "INSERT INTO `" + "stg_dallas_address_dimension_table"
						+ "` (`address_sk`,`Address`,`Zip_Code`,`Latitude`,`Longitude`,`DI_WorkflowFileName`) VALUES (?,?,?,?,?,?)";

				int batchSize_tDBOutput_3 = 100;
				int batchSizeCounter_tDBOutput_3 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tDBOutput_4 begin ] start
				 */

				ok_Hash.put("tDBOutput_4", false);
				start_Hash.put("tDBOutput_4", System.currentTimeMillis());

				currentComponent = "tDBOutput_4";

				cLabel = "target_mysql";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "facility_dimension");

				int tos_count_tDBOutput_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBOutput_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBOutput_4 = new StringBuilder();
							log4jParamters_tDBOutput_4.append("Parameters:");
							log4jParamters_tDBOutput_4.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("DBNAME" + " = " + "\"midterm\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USER" + " = " + "\"root\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:aVy+iBUXV1A7nPIWwuSGzC+kK9wLkO37MazVacrn+pwW2TVfvv4jyw==")
									.substring(0, 4) + "...");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4
									.append("TABLE" + " = " + "\"stg_dallas_facility_dimension_table\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("TABLE_ACTION" + " = " + "DROP_IF_EXISTS_AND_CREATE");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("EXTENDINSERT" + " = " + "true");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("NB_ROWS_PER_INSERT" + " = " + "100");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("COMMIT_EVERY" + " = " + "10000");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("ADD_COLS" + " = " + "[]");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_tDBOutput_4.append(" | ");
							log4jParamters_tDBOutput_4.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
							log4jParamters_tDBOutput_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBOutput_4 - " + (log4jParamters_tDBOutput_4));
						}
					}
					new BytesLimit65535_tDBOutput_4().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBOutput_4", "target_mysql", "tMysqlOutput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tDBOutput_4 = 0;
				int nb_line_update_tDBOutput_4 = 0;
				int nb_line_inserted_tDBOutput_4 = 0;
				int nb_line_deleted_tDBOutput_4 = 0;
				int nb_line_rejected_tDBOutput_4 = 0;

				int deletedCount_tDBOutput_4 = 0;
				int updatedCount_tDBOutput_4 = 0;
				int insertedCount_tDBOutput_4 = 0;
				int rowsToCommitCount_tDBOutput_4 = 0;
				int rejectedCount_tDBOutput_4 = 0;

				String tableName_tDBOutput_4 = "stg_dallas_facility_dimension_table";
				boolean whetherReject_tDBOutput_4 = false;

				java.util.Calendar calendar_tDBOutput_4 = java.util.Calendar.getInstance();
				calendar_tDBOutput_4.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				calendar_tDBOutput_4.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				long date_tDBOutput_4;

				java.sql.Connection conn_tDBOutput_4 = null;

				String properties_tDBOutput_4 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_4 == null || properties_tDBOutput_4.trim().length() == 0) {
					properties_tDBOutput_4 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_4.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_4 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_4.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_4 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_4 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "midterm" + "?"
						+ properties_tDBOutput_4;

				String driverClass_tDBOutput_4 = "com.mysql.cj.jdbc.Driver";

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Driver ClassName: ") + (driverClass_tDBOutput_4) + ("."));
				String dbUser_tDBOutput_4 = "root";

				final String decryptedPassword_tDBOutput_4 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:lPgqGUl578cw/pPehfufw3kXdGCHlX/kp5UaxlRQR68Z1h3stqhpmQ==");

				String dbPwd_tDBOutput_4 = decryptedPassword_tDBOutput_4;
				java.lang.Class.forName(driverClass_tDBOutput_4);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Connection attempts to '") + (url_tDBOutput_4)
							+ ("' with the username '") + (dbUser_tDBOutput_4) + ("'."));
				conn_tDBOutput_4 = java.sql.DriverManager.getConnection(url_tDBOutput_4, dbUser_tDBOutput_4,
						dbPwd_tDBOutput_4);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Connection to '") + (url_tDBOutput_4) + ("' has succeeded."));

				resourceMap.put("conn_tDBOutput_4", conn_tDBOutput_4);

				conn_tDBOutput_4.setAutoCommit(false);
				int commitEvery_tDBOutput_4 = 10000;
				int commitCounter_tDBOutput_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Connection is set auto commit to '")
							+ (conn_tDBOutput_4.getAutoCommit()) + ("'."));

				int count_tDBOutput_4 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_4 = conn_tDBOutput_4.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_4 = dbMetaData_tDBOutput_4.getTables("midterm", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_4 = false;
				while (rsTable_tDBOutput_4.next()) {
					String table_tDBOutput_4 = rsTable_tDBOutput_4.getString("TABLE_NAME");
					if (table_tDBOutput_4.equalsIgnoreCase("stg_dallas_facility_dimension_table")) {
						whetherExist_tDBOutput_4 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_4) {
					try (java.sql.Statement stmtDrop_tDBOutput_4 = conn_tDBOutput_4.createStatement()) {
						if (log.isDebugEnabled())
							log.debug(
									"tDBOutput_4 - " + ("Dropping") + (" table '") + (tableName_tDBOutput_4) + ("'."));
						stmtDrop_tDBOutput_4.execute("DROP TABLE `" + tableName_tDBOutput_4 + "`");
						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("Drop") + (" table '") + (tableName_tDBOutput_4)
									+ ("' has succeeded."));
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_4 = conn_tDBOutput_4.createStatement()) {
					if (log.isDebugEnabled())
						log.debug("tDBOutput_4 - " + ("Creating") + (" table '") + (tableName_tDBOutput_4) + ("'."));
					stmtCreate_tDBOutput_4.execute("CREATE TABLE `" + tableName_tDBOutput_4
							+ "`(`facility_sk` INT(0)   not null ,`Inspection_ID` VARCHAR(10)  ,`Restaurant_Name` VARCHAR(65)  ,`DI_WorkflowFileName` VARCHAR(200)  ,`Facility_Type` VARCHAR(200)  ,primary key(`facility_sk`))");
					if (log.isDebugEnabled())
						log.debug("tDBOutput_4 - " + ("Create") + (" table '") + (tableName_tDBOutput_4)
								+ ("' has succeeded."));
				}

				String insert_tDBOutput_4 = "INSERT INTO `" + "stg_dallas_facility_dimension_table"
						+ "` (`facility_sk`,`Inspection_ID`,`Restaurant_Name`,`DI_WorkflowFileName`,`Facility_Type`) VALUES (?,?,?,?,?)";

				int batchSize_tDBOutput_4 = 100;
				int batchSizeCounter_tDBOutput_4 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_4 = conn_tDBOutput_4.prepareStatement(insert_tDBOutput_4);
				resourceMap.put("pstmt_tDBOutput_4", pstmt_tDBOutput_4);

				/**
				 * [tDBOutput_4 begin ] stop
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
					int address_sk;
					int facility_sk;
					int inspection_sk;
					int date_sk;
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_inspection_fact_tMap_1 = 0;

				inspection_factStruct inspection_fact_tmp = new inspection_factStruct();
				int count_date_dimension_tMap_1 = 0;

				date_dimensionStruct date_dimension_tmp = new date_dimensionStruct();
				int count_address_dimension_tMap_1 = 0;

				address_dimensionStruct address_dimension_tmp = new address_dimensionStruct();
				int count_facility_dimension_tMap_1 = 0;

				facility_dimensionStruct facility_dimension_tmp = new facility_dimensionStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				cLabel = "target_mysql";

				int tos_count_tDBInput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_1 = new StringBuilder();
							log4jParamters_tDBInput_1.append("Parameters:");
							log4jParamters_tDBInput_1.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("DBNAME" + " = " + "\"midterm\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USER" + " = " + "\"root\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:Y7tc3OS9JC8iAmV5295zIjIx0Hf+9FLuo7OavlfBhZDwTIeQPuwkYg==")
									.substring(0, 4) + "...");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TABLE" + " = " + "\"stg_finaldallasconnectiontable\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1
									.append("QUERY" + " = " + "\"select * from stg_finaldallasconnectiontable\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
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
									+ ("Violation_Points___1") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___2") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___3") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___4") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___5") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___6") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___7") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___8") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___9") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___10") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___11") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___12") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___13") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___14") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___15") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___16") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___17") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___18") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___19") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___20") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___21") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___22") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___23") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___24") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Violation_Points___25") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Inspection_Month") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Inspection_Year") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Address")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Latitude") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("Longitude") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("DI_CreateDate") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("DI_WorkFlowFileNme") + "}]");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
							log4jParamters_tDBInput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_1 - " + (log4jParamters_tDBInput_1));
						}
					}
					new BytesLimit65535_tDBInput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_1", "target_mysql", "tMysqlInput");
					talendJobLogProcess(globalMap);
				}

				java.util.Calendar calendar_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_1 = calendar_tDBInput_1.getTime();
				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "root";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:mNd9S5kt7vBUTnqur87w8QFyNwXOU68eg0fxZGPkM4H962vA9XFBRQ==");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String properties_tDBInput_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_1 == null || properties_tDBInput_1.trim().length() == 0) {
					properties_tDBInput_1 = "";
				}
				String url_tDBInput_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "midterm" + "?"
						+ properties_tDBInput_1;

				log.debug("tDBInput_1 - Driver ClassName: " + driverClass_tDBInput_1 + ".");

				log.debug("tDBInput_1 - Connection attempt to '" + url_tDBInput_1 + "' with the username '"
						+ dbUser_tDBInput_1 + "'.");

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);
				log.debug("tDBInput_1 - Connection to '" + url_tDBInput_1 + "' has succeeded.");

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "select * from stg_finaldallasconnectiontable";

				log.debug("tDBInput_1 - Executing the query: '" + dbquery_tDBInput_1 + "'.");

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);

				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					log.debug("tDBInput_1 - Retrieving records from the database.");

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.Inspection_ID = null;
						} else {

							row1.Inspection_ID = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.Restaurant_Name = null;
						} else {

							row1.Restaurant_Name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.Inspection_Type = null;
						} else {

							row1.Inspection_Type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.Inspection_Date = null;
						} else {

							if (rs_tDBInput_1.getString(4) != null) {
								String dateString_tDBInput_1 = rs_tDBInput_1.getString(4);
								if (!("0000-00-00").equals(dateString_tDBInput_1)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_1)) {
									row1.Inspection_Date = rs_tDBInput_1.getTimestamp(4);
								} else {
									row1.Inspection_Date = (java.util.Date) year0_tDBInput_1.clone();
								}
							} else {
								row1.Inspection_Date = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.Inspection_Score = null;
						} else {

							row1.Inspection_Score = rs_tDBInput_1.getInt(5);
							if (rs_tDBInput_1.wasNull()) {
								row1.Inspection_Score = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.Street_Number = null;
						} else {

							row1.Street_Number = rs_tDBInput_1.getInt(6);
							if (rs_tDBInput_1.wasNull()) {
								row1.Street_Number = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.Street_Name = null;
						} else {

							row1.Street_Name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.Street_Direction = null;
						} else {

							row1.Street_Direction = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, false);
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.Street_Type = null;
						} else {

							row1.Street_Type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 9, false);
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.Street_Unit = null;
						} else {

							row1.Street_Unit = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row1.Street_Address = null;
						} else {

							row1.Street_Address = routines.system.JDBCUtil.getString(rs_tDBInput_1, 11, false);
						}
						if (colQtyInRs_tDBInput_1 < 12) {
							row1.Zip_Code = null;
						} else {

							row1.Zip_Code = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, false);
						}
						if (colQtyInRs_tDBInput_1 < 13) {
							row1.Violation_Points___1 = null;
						} else {

							row1.Violation_Points___1 = rs_tDBInput_1.getDouble(13);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___1 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 14) {
							row1.Violation_Points___2 = null;
						} else {

							row1.Violation_Points___2 = rs_tDBInput_1.getDouble(14);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___2 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 15) {
							row1.Violation_Points___3 = null;
						} else {

							row1.Violation_Points___3 = rs_tDBInput_1.getDouble(15);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___3 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 16) {
							row1.Violation_Points___4 = null;
						} else {

							row1.Violation_Points___4 = rs_tDBInput_1.getDouble(16);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___4 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 17) {
							row1.Violation_Points___5 = null;
						} else {

							row1.Violation_Points___5 = rs_tDBInput_1.getDouble(17);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___5 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 18) {
							row1.Violation_Points___6 = null;
						} else {

							row1.Violation_Points___6 = rs_tDBInput_1.getDouble(18);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___6 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 19) {
							row1.Violation_Points___7 = null;
						} else {

							row1.Violation_Points___7 = rs_tDBInput_1.getDouble(19);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___7 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 20) {
							row1.Violation_Points___8 = null;
						} else {

							row1.Violation_Points___8 = rs_tDBInput_1.getDouble(20);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___8 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 21) {
							row1.Violation_Points___9 = null;
						} else {

							row1.Violation_Points___9 = rs_tDBInput_1.getDouble(21);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___9 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 22) {
							row1.Violation_Points___10 = null;
						} else {

							row1.Violation_Points___10 = rs_tDBInput_1.getDouble(22);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___10 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 23) {
							row1.Violation_Points___11 = null;
						} else {

							row1.Violation_Points___11 = rs_tDBInput_1.getDouble(23);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___11 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 24) {
							row1.Violation_Points___12 = null;
						} else {

							row1.Violation_Points___12 = rs_tDBInput_1.getDouble(24);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___12 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 25) {
							row1.Violation_Points___13 = null;
						} else {

							row1.Violation_Points___13 = rs_tDBInput_1.getDouble(25);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___13 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 26) {
							row1.Violation_Points___14 = null;
						} else {

							row1.Violation_Points___14 = rs_tDBInput_1.getDouble(26);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___14 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 27) {
							row1.Violation_Points___15 = null;
						} else {

							row1.Violation_Points___15 = rs_tDBInput_1.getDouble(27);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___15 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 28) {
							row1.Violation_Points___16 = null;
						} else {

							row1.Violation_Points___16 = rs_tDBInput_1.getDouble(28);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___16 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 29) {
							row1.Violation_Points___17 = null;
						} else {

							row1.Violation_Points___17 = rs_tDBInput_1.getDouble(29);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___17 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 30) {
							row1.Violation_Points___18 = null;
						} else {

							row1.Violation_Points___18 = rs_tDBInput_1.getDouble(30);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___18 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 31) {
							row1.Violation_Points___19 = null;
						} else {

							row1.Violation_Points___19 = rs_tDBInput_1.getDouble(31);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___19 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 32) {
							row1.Violation_Points___20 = null;
						} else {

							row1.Violation_Points___20 = rs_tDBInput_1.getDouble(32);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___20 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 33) {
							row1.Violation_Points___21 = null;
						} else {

							row1.Violation_Points___21 = rs_tDBInput_1.getDouble(33);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___21 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 34) {
							row1.Violation_Points___22 = null;
						} else {

							row1.Violation_Points___22 = rs_tDBInput_1.getDouble(34);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___22 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 35) {
							row1.Violation_Points___23 = null;
						} else {

							row1.Violation_Points___23 = rs_tDBInput_1.getDouble(35);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___23 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 36) {
							row1.Violation_Points___24 = null;
						} else {

							row1.Violation_Points___24 = rs_tDBInput_1.getDouble(36);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___24 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 37) {
							row1.Violation_Points___25 = null;
						} else {

							row1.Violation_Points___25 = rs_tDBInput_1.getDouble(37);
							if (rs_tDBInput_1.wasNull()) {
								row1.Violation_Points___25 = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 38) {
							row1.Inspection_Month = null;
						} else {

							row1.Inspection_Month = routines.system.JDBCUtil.getString(rs_tDBInput_1, 38, false);
						}
						if (colQtyInRs_tDBInput_1 < 39) {
							row1.Inspection_Year = null;
						} else {

							row1.Inspection_Year = routines.system.JDBCUtil.getString(rs_tDBInput_1, 39, false);
						}
						if (colQtyInRs_tDBInput_1 < 40) {
							row1.Address = null;
						} else {

							row1.Address = routines.system.JDBCUtil.getString(rs_tDBInput_1, 40, false);
						}
						if (colQtyInRs_tDBInput_1 < 41) {
							row1.Latitude = null;
						} else {

							row1.Latitude = rs_tDBInput_1.getBigDecimal(41);
							if (rs_tDBInput_1.wasNull()) {
								row1.Latitude = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 42) {
							row1.Longitude = null;
						} else {

							row1.Longitude = rs_tDBInput_1.getBigDecimal(42);
							if (rs_tDBInput_1.wasNull()) {
								row1.Longitude = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 43) {
							row1.DI_CreateDate = null;
						} else {

							if (rs_tDBInput_1.getString(43) != null) {
								String dateString_tDBInput_1 = rs_tDBInput_1.getString(43);
								if (!("0000-00-00").equals(dateString_tDBInput_1)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_1)) {
									row1.DI_CreateDate = rs_tDBInput_1.getTimestamp(43);
								} else {
									row1.DI_CreateDate = (java.util.Date) year0_tDBInput_1.clone();
								}
							} else {
								row1.DI_CreateDate = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 44) {
							row1.DI_WorkFlowFileNme = null;
						} else {

							row1.DI_WorkFlowFileNme = routines.system.JDBCUtil.getString(rs_tDBInput_1, 44, false);
						}

						log.debug("tDBInput_1 - Retrieving the record " + nb_line_tDBInput_1 + ".");

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						cLabel = "target_mysql";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						cLabel = "target_mysql";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_1 main ] start
						 */

						currentComponent = "tMap_1";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "target_mysql", "tMysqlInput", "tMap_1", "tMap_1", "tMap"

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

							Var__tMap_1__Struct Var = Var__tMap_1;
							Var.address_sk = Numeric.sequence("address_sk", 1, 1);
							Var.facility_sk = Numeric.sequence("facility_sk", 1, 1);
							Var.inspection_sk = Numeric.sequence("inspection_sk", 1, 1);
							Var.date_sk = Numeric.sequence("date_sk", 1, 1);// ###############################
							// ###############################
							// # Output tables

							inspection_fact = null;
							date_dimension = null;
							address_dimension = null;
							facility_dimension = null;

// # Output table : 'inspection_fact'
							count_inspection_fact_tMap_1++;

							inspection_fact_tmp.inspection_sk = Var.inspection_sk;
							inspection_fact_tmp.facility_sk = Var.facility_sk;
							inspection_fact_tmp.date_sk = Var.date_sk;
							inspection_fact_tmp.address_sk = Var.address_sk;
							inspection_fact_tmp.Inspection_ID = row1.Inspection_ID;
							inspection_fact_tmp.Inspection_Type = row1.Inspection_Type;
							inspection_fact_tmp.TotalViolationScore = (int) Math.round(row1.Violation_Points___1
									+ row1.Violation_Points___2 + row1.Violation_Points___3 + row1.Violation_Points___4
									+ row1.Violation_Points___5 + row1.Violation_Points___6 + row1.Violation_Points___7
									+ row1.Violation_Points___8 + row1.Violation_Points___9 + row1.Violation_Points___10
									+ row1.Violation_Points___11 + row1.Violation_Points___12
									+ row1.Violation_Points___13 + row1.Violation_Points___14
									+ row1.Violation_Points___15 + row1.Violation_Points___16
									+ row1.Violation_Points___17 + row1.Violation_Points___18
									+ row1.Violation_Points___19 + row1.Violation_Points___20
									+ row1.Violation_Points___21 + row1.Violation_Points___22
									+ row1.Violation_Points___23 + row1.Violation_Points___24
									+ row1.Violation_Points___25);
							inspection_fact_tmp.Inspection_Result = (Double) (row1.Violation_Points___1
									+ row1.Violation_Points___2 + row1.Violation_Points___3 + row1.Violation_Points___4
									+ row1.Violation_Points___5 + row1.Violation_Points___6 + row1.Violation_Points___7
									+ row1.Violation_Points___8 + row1.Violation_Points___9 + row1.Violation_Points___10
									+ row1.Violation_Points___11 + row1.Violation_Points___12
									+ row1.Violation_Points___13 + row1.Violation_Points___14
									+ row1.Violation_Points___15 + row1.Violation_Points___16
									+ row1.Violation_Points___17 + row1.Violation_Points___18
									+ row1.Violation_Points___19 + row1.Violation_Points___20
									+ row1.Violation_Points___21 + row1.Violation_Points___22
									+ row1.Violation_Points___23 + row1.Violation_Points___24
									+ row1.Violation_Points___25) > 60
											? "Fail"
											: (Double) (row1.Violation_Points___1 + row1.Violation_Points___2
													+ row1.Violation_Points___3 + row1.Violation_Points___4
													+ row1.Violation_Points___5 + row1.Violation_Points___6
													+ row1.Violation_Points___7 + row1.Violation_Points___8
													+ row1.Violation_Points___9 + row1.Violation_Points___10
													+ row1.Violation_Points___11 + row1.Violation_Points___12
													+ row1.Violation_Points___13 + row1.Violation_Points___14
													+ row1.Violation_Points___15 + row1.Violation_Points___16
													+ row1.Violation_Points___17 + row1.Violation_Points___18
													+ row1.Violation_Points___19 + row1.Violation_Points___20
													+ row1.Violation_Points___21 + row1.Violation_Points___22
													+ row1.Violation_Points___23 + row1.Violation_Points___24
													+ row1.Violation_Points___25) <= 60
													&& (Double) (row1.Violation_Points___1 + row1.Violation_Points___2
															+ row1.Violation_Points___3 + row1.Violation_Points___4
															+ row1.Violation_Points___5 + row1.Violation_Points___6
															+ row1.Violation_Points___7 + row1.Violation_Points___8
															+ row1.Violation_Points___9 + row1.Violation_Points___10
															+ row1.Violation_Points___11 + row1.Violation_Points___12
															+ row1.Violation_Points___13 + row1.Violation_Points___14
															+ row1.Violation_Points___15 + row1.Violation_Points___16
															+ row1.Violation_Points___17 + row1.Violation_Points___18
															+ row1.Violation_Points___19 + row1.Violation_Points___20
															+ row1.Violation_Points___21 + row1.Violation_Points___22
															+ row1.Violation_Points___23 + row1.Violation_Points___24
															+ row1.Violation_Points___25) > 30 ? "Pass with Warning"
																	: "Pass";
							;
							inspection_fact_tmp.DI_WorkflowFileName = "Dallas Workflow";
							inspection_fact = inspection_fact_tmp;
							log.debug("tMap_1 - Outputting the record " + count_inspection_fact_tMap_1
									+ " of the output table 'inspection_fact'.");

// # Output table : 'date_dimension'
							count_date_dimension_tMap_1++;

							date_dimension_tmp.date_sk = Var.date_sk;
							date_dimension_tmp.Inspection_Date = TalendDate.parseDate("yyyy-MM-dd",
									TalendDate.formatDate("yyyy-MM-dd", row1.Inspection_Date));
							date_dimension_tmp.Inspection_Month = TalendDate.formatDate("MM", row1.Inspection_Date);
							date_dimension_tmp.Inspection_Year = TalendDate.formatDate("yyyy", row1.Inspection_Date);
							date_dimension_tmp.Inspection_Day = TalendDate.formatDate("EEEE", row1.Inspection_Date);
							date_dimension_tmp.DI_WorkflowFileName = "Dallas Workflow";
							date_dimension = date_dimension_tmp;
							log.debug("tMap_1 - Outputting the record " + count_date_dimension_tMap_1
									+ " of the output table 'date_dimension'.");

// # Output table : 'address_dimension'
							count_address_dimension_tMap_1++;

							address_dimension_tmp.address_sk = Var.address_sk;
							address_dimension_tmp.Address = row1.Address;
							address_dimension_tmp.Zip_Code = row1.Zip_Code;
							address_dimension_tmp.Latitude = row1.Latitude;
							address_dimension_tmp.Longitude = row1.Longitude;
							address_dimension_tmp.DI_WorkflowFileName = "Dallas Workflow";
							address_dimension = address_dimension_tmp;
							log.debug("tMap_1 - Outputting the record " + count_address_dimension_tMap_1
									+ " of the output table 'address_dimension'.");

// # Output table : 'facility_dimension'
							count_facility_dimension_tMap_1++;

							facility_dimension_tmp.facility_sk = Var.facility_sk;
							facility_dimension_tmp.Inspection_ID = row1.Inspection_ID;
							facility_dimension_tmp.Restaurant_Name = row1.Restaurant_Name;
							facility_dimension_tmp.DI_WorkflowFileName = "Dallas Workflow";
							facility_dimension_tmp.Facility_Type = "Restaurant";
							facility_dimension = facility_dimension_tmp;
							log.debug("tMap_1 - Outputting the record " + count_facility_dimension_tMap_1
									+ " of the output table 'facility_dimension'.");

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
// Start of branch "inspection_fact"
						if (inspection_fact != null) {

							/**
							 * [tDBOutput_1 main ] start
							 */

							currentComponent = "tDBOutput_1";

							cLabel = "target_mysql";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "inspection_fact", "tMap_1", "tMap_1", "tMap", "tDBOutput_1", "target_mysql",
									"tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("inspection_fact - "
										+ (inspection_fact == null ? "" : inspection_fact.toLogString()));
							}

							whetherReject_tDBOutput_1 = false;
							pstmt_tDBOutput_1.setInt(1, inspection_fact.inspection_sk);

							pstmt_tDBOutput_1.setInt(2, inspection_fact.facility_sk);

							pstmt_tDBOutput_1.setInt(3, inspection_fact.date_sk);

							pstmt_tDBOutput_1.setInt(4, inspection_fact.address_sk);

							if (inspection_fact.Inspection_ID == null) {
								pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(5, inspection_fact.Inspection_ID);
							}

							if (inspection_fact.Inspection_Type == null) {
								pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(6, inspection_fact.Inspection_Type);
							}

							if (inspection_fact.TotalViolationScore == null) {
								pstmt_tDBOutput_1.setNull(7, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_1.setInt(7, inspection_fact.TotalViolationScore);
							}

							if (inspection_fact.Inspection_Result == null) {
								pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(8, inspection_fact.Inspection_Result);
							}

							if (inspection_fact.DI_WorkflowFileName == null) {
								pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(9, inspection_fact.DI_WorkflowFileName);
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
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
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
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
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

						} // End of branch "inspection_fact"

// Start of branch "date_dimension"
						if (date_dimension != null) {

							/**
							 * [tDBOutput_2 main ] start
							 */

							currentComponent = "tDBOutput_2";

							cLabel = "target_mysql";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "date_dimension", "tMap_1", "tMap_1", "tMap", "tDBOutput_2", "target_mysql",
									"tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("date_dimension - "
										+ (date_dimension == null ? "" : date_dimension.toLogString()));
							}

							whetherReject_tDBOutput_2 = false;
							pstmt_tDBOutput_2.setInt(1, date_dimension.date_sk);

							if (date_dimension.Inspection_Date != null) {
								date_tDBOutput_2 = date_dimension.Inspection_Date.getTime();
								if (date_tDBOutput_2 < year1_tDBOutput_2 || date_tDBOutput_2 >= year10000_tDBOutput_2) {
									pstmt_tDBOutput_2.setString(2, "0000-00-00 00:00:00");
								} else {
									pstmt_tDBOutput_2.setTimestamp(2, new java.sql.Timestamp(date_tDBOutput_2));
								}
							} else {
								pstmt_tDBOutput_2.setNull(2, java.sql.Types.DATE);
							}

							if (date_dimension.Inspection_Month == null) {
								pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(3, date_dimension.Inspection_Month);
							}

							if (date_dimension.Inspection_Year == null) {
								pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(4, date_dimension.Inspection_Year);
							}

							if (date_dimension.Inspection_Day == null) {
								pstmt_tDBOutput_2.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(5, date_dimension.Inspection_Day);
							}

							if (date_dimension.DI_WorkflowFileName == null) {
								pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(6, date_dimension.DI_WorkflowFileName);
							}

							pstmt_tDBOutput_2.addBatch();
							nb_line_tDBOutput_2++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_2 - " + ("Adding the record ") + (nb_line_tDBOutput_2)
										+ (" to the ") + ("INSERT") + (" batch."));
							batchSizeCounter_tDBOutput_2++;
							if (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2) {
								try {
									int countSum_tDBOutput_2 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
									System.err.println(e.getMessage());
									log.error("tDBOutput_2 - " + (e.getMessage()));
								}

								batchSizeCounter_tDBOutput_2 = 0;
							}
							commitCounter_tDBOutput_2++;

							if (commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {

								try {
									int countSum_tDBOutput_2 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
									System.err.println(e.getMessage());
									log.error("tDBOutput_2 - " + (e.getMessage()));

								}
								if (rowsToCommitCount_tDBOutput_2 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("Connection starting to commit ")
												+ (rowsToCommitCount_tDBOutput_2) + (" record(s)."));
								}
								conn_tDBOutput_2.commit();
								if (rowsToCommitCount_tDBOutput_2 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_2 - " + ("Connection commit has succeeded."));
									rowsToCommitCount_tDBOutput_2 = 0;
								}
								commitCounter_tDBOutput_2 = 0;
							}

							tos_count_tDBOutput_2++;

							/**
							 * [tDBOutput_2 main ] stop
							 */

							/**
							 * [tDBOutput_2 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_2";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_2 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_2 process_data_end ] start
							 */

							currentComponent = "tDBOutput_2";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_2 process_data_end ] stop
							 */

						} // End of branch "date_dimension"

// Start of branch "address_dimension"
						if (address_dimension != null) {

							/**
							 * [tDBOutput_3 main ] start
							 */

							currentComponent = "tDBOutput_3";

							cLabel = "target_mysql";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "address_dimension", "tMap_1", "tMap_1", "tMap", "tDBOutput_3", "target_mysql",
									"tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("address_dimension - "
										+ (address_dimension == null ? "" : address_dimension.toLogString()));
							}

							whetherReject_tDBOutput_3 = false;
							pstmt_tDBOutput_3.setInt(1, address_dimension.address_sk);

							if (address_dimension.Address == null) {
								pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(2, address_dimension.Address);
							}

							if (address_dimension.Zip_Code == null) {
								pstmt_tDBOutput_3.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(3, address_dimension.Zip_Code);
							}

							pstmt_tDBOutput_3.setBigDecimal(4, address_dimension.Latitude);

							pstmt_tDBOutput_3.setBigDecimal(5, address_dimension.Longitude);

							if (address_dimension.DI_WorkflowFileName == null) {
								pstmt_tDBOutput_3.setNull(6, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(6, address_dimension.DI_WorkflowFileName);
							}

							pstmt_tDBOutput_3.addBatch();
							nb_line_tDBOutput_3++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_3 - " + ("Adding the record ") + (nb_line_tDBOutput_3)
										+ (" to the ") + ("INSERT") + (" batch."));
							batchSizeCounter_tDBOutput_3++;
							if (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3) {
								try {
									int countSum_tDBOutput_3 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
									System.err.println(e.getMessage());
									log.error("tDBOutput_3 - " + (e.getMessage()));
								}

								batchSizeCounter_tDBOutput_3 = 0;
							}
							commitCounter_tDBOutput_3++;

							if (commitEvery_tDBOutput_3 <= commitCounter_tDBOutput_3) {

								try {
									int countSum_tDBOutput_3 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
									System.err.println(e.getMessage());
									log.error("tDBOutput_3 - " + (e.getMessage()));

								}
								if (rowsToCommitCount_tDBOutput_3 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Connection starting to commit ")
												+ (rowsToCommitCount_tDBOutput_3) + (" record(s)."));
								}
								conn_tDBOutput_3.commit();
								if (rowsToCommitCount_tDBOutput_3 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_3 - " + ("Connection commit has succeeded."));
									rowsToCommitCount_tDBOutput_3 = 0;
								}
								commitCounter_tDBOutput_3 = 0;
							}

							tos_count_tDBOutput_3++;

							/**
							 * [tDBOutput_3 main ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_3";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_3 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_end ] start
							 */

							currentComponent = "tDBOutput_3";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_3 process_data_end ] stop
							 */

						} // End of branch "address_dimension"

// Start of branch "facility_dimension"
						if (facility_dimension != null) {

							/**
							 * [tDBOutput_4 main ] start
							 */

							currentComponent = "tDBOutput_4";

							cLabel = "target_mysql";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "facility_dimension", "tMap_1", "tMap_1", "tMap", "tDBOutput_4", "target_mysql",
									"tMysqlOutput"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("facility_dimension - "
										+ (facility_dimension == null ? "" : facility_dimension.toLogString()));
							}

							whetherReject_tDBOutput_4 = false;
							pstmt_tDBOutput_4.setInt(1, facility_dimension.facility_sk);

							if (facility_dimension.Inspection_ID == null) {
								pstmt_tDBOutput_4.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(2, facility_dimension.Inspection_ID);
							}

							if (facility_dimension.Restaurant_Name == null) {
								pstmt_tDBOutput_4.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(3, facility_dimension.Restaurant_Name);
							}

							if (facility_dimension.DI_WorkflowFileName == null) {
								pstmt_tDBOutput_4.setNull(4, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(4, facility_dimension.DI_WorkflowFileName);
							}

							if (facility_dimension.Facility_Type == null) {
								pstmt_tDBOutput_4.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(5, facility_dimension.Facility_Type);
							}

							pstmt_tDBOutput_4.addBatch();
							nb_line_tDBOutput_4++;

							if (log.isDebugEnabled())
								log.debug("tDBOutput_4 - " + ("Adding the record ") + (nb_line_tDBOutput_4)
										+ (" to the ") + ("INSERT") + (" batch."));
							batchSizeCounter_tDBOutput_4++;
							if (batchSize_tDBOutput_4 <= batchSizeCounter_tDBOutput_4) {
								try {
									int countSum_tDBOutput_4 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
									System.err.println(e.getMessage());
									log.error("tDBOutput_4 - " + (e.getMessage()));
								}

								batchSizeCounter_tDBOutput_4 = 0;
							}
							commitCounter_tDBOutput_4++;

							if (commitEvery_tDBOutput_4 <= commitCounter_tDBOutput_4) {

								try {
									int countSum_tDBOutput_4 = 0;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("Executing the ") + ("INSERT") + (" batch."));
									for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("The ") + ("INSERT")
												+ (" batch execution has succeeded."));
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
									System.err.println(e.getMessage());
									log.error("tDBOutput_4 - " + (e.getMessage()));

								}
								if (rowsToCommitCount_tDBOutput_4 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("Connection starting to commit ")
												+ (rowsToCommitCount_tDBOutput_4) + (" record(s)."));
								}
								conn_tDBOutput_4.commit();
								if (rowsToCommitCount_tDBOutput_4 != 0) {
									if (log.isDebugEnabled())
										log.debug("tDBOutput_4 - " + ("Connection commit has succeeded."));
									rowsToCommitCount_tDBOutput_4 = 0;
								}
								commitCounter_tDBOutput_4 = 0;
							}

							tos_count_tDBOutput_4++;

							/**
							 * [tDBOutput_4 main ] stop
							 */

							/**
							 * [tDBOutput_4 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_4";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_4 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_4 process_data_end ] start
							 */

							currentComponent = "tDBOutput_4";

							cLabel = "target_mysql";

							/**
							 * [tDBOutput_4 process_data_end ] stop
							 */

						} // End of branch "facility_dimension"

						/**
						 * [tMap_1 process_data_end ] start
						 */

						currentComponent = "tMap_1";

						/**
						 * [tMap_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						cLabel = "target_mysql";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

						cLabel = "target_mysql";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						log.debug("tDBInput_1 - Closing the connection to the database.");

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("tDBInput_1 - Connection to the database closed.");

					}

				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
				log.debug("tDBInput_1 - Retrieved records count: " + nb_line_tDBInput_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Done."));

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_1 - Written records count in the table 'inspection_fact': "
						+ count_inspection_fact_tMap_1 + ".");
				log.debug("tMap_1 - Written records count in the table 'date_dimension': " + count_date_dimension_tMap_1
						+ ".");
				log.debug("tMap_1 - Written records count in the table 'address_dimension': "
						+ count_address_dimension_tMap_1 + ".");
				log.debug("tMap_1 - Written records count in the table 'facility_dimension': "
						+ count_facility_dimension_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "target_mysql", "tMysqlInput", "tMap_1", "tMap_1", "tMap", "output")) {
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

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "inspection_fact", 2, 0,
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

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				cLabel = "target_mysql";

				try {
					if (batchSizeCounter_tDBOutput_2 != 0) {
						int countSum_tDBOutput_2 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_2 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					log.error("tDBOutput_2 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_2 = 0;

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");

				}

				resourceMap.put("statementClosed_tDBOutput_2", true);

				if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_2) + (" record(s)."));
				}
				conn_tDBOutput_2.commit();
				if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_2 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Closing the connection to the database."));
				conn_tDBOutput_2.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_2)
							+ (" record(s)."));

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "date_dimension", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tDBOutput_2", "target_mysql", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_2 - " + ("Done."));

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				cLabel = "target_mysql";

				try {
					if (batchSizeCounter_tDBOutput_3 != 0) {
						int countSum_tDBOutput_3 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_3 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					log.error("tDBOutput_3 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_3 = 0;

				if (pstmt_tDBOutput_3 != null) {

					pstmt_tDBOutput_3.close();
					resourceMap.remove("pstmt_tDBOutput_3");

				}

				resourceMap.put("statementClosed_tDBOutput_3", true);

				if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_3 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_3) + (" record(s)."));
				}
				conn_tDBOutput_3.commit();
				if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_3 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_3 = 0;
				}
				commitCounter_tDBOutput_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Closing the connection to the database."));
				conn_tDBOutput_3.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_3", true);

				nb_line_deleted_tDBOutput_3 = nb_line_deleted_tDBOutput_3 + deletedCount_tDBOutput_3;
				nb_line_update_tDBOutput_3 = nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
				nb_line_inserted_tDBOutput_3 = nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
				nb_line_rejected_tDBOutput_3 = nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;

				globalMap.put("tDBOutput_3_NB_LINE", nb_line_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_UPDATED", nb_line_update_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_DELETED", nb_line_deleted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_3)
							+ (" record(s)."));

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "address_dimension", 2,
						0, "tMap_1", "tMap_1", "tMap", "tDBOutput_3", "target_mysql", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_3 - " + ("Done."));

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
				 */

				/**
				 * [tDBOutput_4 end ] start
				 */

				currentComponent = "tDBOutput_4";

				cLabel = "target_mysql";

				try {
					if (batchSizeCounter_tDBOutput_4 != 0) {
						int countSum_tDBOutput_4 = 0;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("Executing the ") + ("INSERT") + (" batch."));
						for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
							countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

						if (log.isDebugEnabled())
							log.debug("tDBOutput_4 - " + ("The ") + ("INSERT") + (" batch execution has succeeded."));

						insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

					}
				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_4 = 0;
					for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
						countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
					}
					rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

					insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

					log.error("tDBOutput_4 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_4 = 0;

				if (pstmt_tDBOutput_4 != null) {

					pstmt_tDBOutput_4.close();
					resourceMap.remove("pstmt_tDBOutput_4");

				}

				resourceMap.put("statementClosed_tDBOutput_4", true);

				if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_4 - " + ("Connection starting to commit ")
								+ (rowsToCommitCount_tDBOutput_4) + (" record(s)."));
				}
				conn_tDBOutput_4.commit();
				if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {

					if (log.isDebugEnabled())
						log.debug("tDBOutput_4 - " + ("Connection commit has succeeded."));
					rowsToCommitCount_tDBOutput_4 = 0;
				}
				commitCounter_tDBOutput_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Closing the connection to the database."));
				conn_tDBOutput_4.close();

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Connection to the database has closed."));
				resourceMap.put("finish_tDBOutput_4", true);

				nb_line_deleted_tDBOutput_4 = nb_line_deleted_tDBOutput_4 + deletedCount_tDBOutput_4;
				nb_line_update_tDBOutput_4 = nb_line_update_tDBOutput_4 + updatedCount_tDBOutput_4;
				nb_line_inserted_tDBOutput_4 = nb_line_inserted_tDBOutput_4 + insertedCount_tDBOutput_4;
				nb_line_rejected_tDBOutput_4 = nb_line_rejected_tDBOutput_4 + rejectedCount_tDBOutput_4;

				globalMap.put("tDBOutput_4_NB_LINE", nb_line_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_UPDATED", nb_line_update_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_DELETED", nb_line_deleted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_4);

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Has ") + ("inserted") + (" ") + (nb_line_inserted_tDBOutput_4)
							+ (" record(s)."));

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "facility_dimension", 2,
						0, "tMap_1", "tMap_1", "tMap", "tDBOutput_4", "target_mysql", "tMysqlOutput", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBOutput_4 - " + ("Done."));

				ok_Hash.put("tDBOutput_4", true);
				end_Hash.put("tDBOutput_4", System.currentTimeMillis());

				/**
				 * [tDBOutput_4 end ] stop
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
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				cLabel = "target_mysql";

				/**
				 * [tDBInput_1 finally ] stop
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

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				cLabel = "target_mysql";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_2.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_2 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								log.error("tDBOutput_2 - " + (errorMessage_tDBOutput_2));
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

				/**
				 * [tDBOutput_3 finally ] start
				 */

				currentComponent = "tDBOutput_3";

				cLabel = "target_mysql";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
						if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_3")) != null) {
							pstmtToClose_tDBOutput_3.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_3") == null) {
						java.sql.Connection ctn_tDBOutput_3 = null;
						if ((ctn_tDBOutput_3 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_3")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_3 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_3.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_3 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_3) {
								String errorMessage_tDBOutput_3 = "failed to close the connection in tDBOutput_3 :"
										+ sqlEx_tDBOutput_3.getMessage();
								log.error("tDBOutput_3 - " + (errorMessage_tDBOutput_3));
								System.err.println(errorMessage_tDBOutput_3);
							}
						}
					}
				}

				/**
				 * [tDBOutput_3 finally ] stop
				 */

				/**
				 * [tDBOutput_4 finally ] start
				 */

				currentComponent = "tDBOutput_4";

				cLabel = "target_mysql";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_4") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_4 = null;
						if ((pstmtToClose_tDBOutput_4 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_4")) != null) {
							pstmtToClose_tDBOutput_4.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_4") == null) {
						java.sql.Connection ctn_tDBOutput_4 = null;
						if ((ctn_tDBOutput_4 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_4")) != null) {
							try {
								if (log.isDebugEnabled())
									log.debug("tDBOutput_4 - " + ("Closing the connection to the database."));
								ctn_tDBOutput_4.close();
								if (log.isDebugEnabled())
									log.debug("tDBOutput_4 - " + ("Connection to the database has closed."));
							} catch (java.sql.SQLException sqlEx_tDBOutput_4) {
								String errorMessage_tDBOutput_4 = "failed to close the connection in tDBOutput_4 :"
										+ sqlEx_tDBOutput_4.getMessage();
								log.error("tDBOutput_4 - " + (errorMessage_tDBOutput_4));
								System.err.println(errorMessage_tDBOutput_4);
							}
						}
					}
				}

				/**
				 * [tDBOutput_4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "talendJobLog");
		org.slf4j.MDC.put("_subJobPid", "YVDHkW_" + subJobPidCounter.getAndIncrement());

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
		final load_dallas_dim load_dallas_dimClass = new load_dallas_dim();

		int exitCode = load_dallas_dimClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'load_dallas_dim' - Done.");
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
		log.info("TalendJob: 'load_dallas_dim' - Start.");

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
		org.slf4j.MDC.put("_jobRepositoryId", "_WRcZcNgLEe6IueXiohRFmg");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-03-01T21:47:14.276939900Z");

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
			java.io.InputStream inContext = load_dallas_dim.class.getClassLoader()
					.getResourceAsStream("stg_dallas/load_dallas_dim_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = load_dallas_dim.class.getClassLoader()
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
		log.info("TalendJob: 'load_dallas_dim' - Started.");
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
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : load_dallas_dim");
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
		log.info("TalendJob: 'load_dallas_dim' - Finished - status: " + status + " returnCode: " + returnCode);

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
 * 320977 characters generated by Talend Cloud Data Fabric on the 1 March 2024
 * at 4:47:14 PM GMT-05:00
 ************************************************************************************************/