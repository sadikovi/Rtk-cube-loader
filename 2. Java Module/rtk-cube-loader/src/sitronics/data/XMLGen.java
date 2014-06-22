package sitronics.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import sitronics.data.utils.Common;
import sitronics.data.utils.consts.Consts;

public class XMLGen {
    
    //models indexes
    public static final int PHYSICAL_MODEL_INDEX = 5;
    public static final int BUSINESS_MODEL_INDEX = 7;
    public static final int PRESENTATION_MODEL_INDEX = 9;
    
    //physical model constants
    public static final int DATABASE_INDEX = 0;
    public static final int CONNECTION_POOL_INDEX = 1;
    public static final int PHYSICAL_CATALOG_INDEX = 2;
    public static final int SCHEMA_INDEX = 3;
    public static final int PHYSICAL_TABLE_INDEX = 4;
    public static final int PHYSICAL_COLUMN_INDEX = 5;
    public static final int PHYSICAL_KEY_INDEX = 6;
    public static final int PHYSICAL_FOREIGN_KEY_INDEX = 7;
    public static final int VARIABLE_INDEX = 8;
    
    //business model constants
    public static final int LOGICAL_TABLE_INDEX = 4;
    public static final int LOGICAL_COLUMN_INDEX = 5;
    public static final int LOGICAL_KEY_INDEX = 6;
    public static final int LOGICAL_COMPLEX_JOIN_INDEX = 7;
    public static final int LOGICAL_TABLE_SOURCE_INDEX = 8;
    public static final int MEASURE_DFN_INDEX = 2;
    public static final int AGG_RULE_INDEX = 3;
    
    //presentation model constants
    public static final int PRESENTATION_TABLE_INDEX = 1;
    public static final int PRESENTATION_COLUMN_INDEX = 2;
    
    
    public XMLGen() {
        super();
    }
    
    private static XMLGen newInstance = new XMLGen();
    
    public static XMLGen getInstance() {
        return newInstance; 
    }
    
    public static boolean isLogicalCreatedDate(String lCName) {
        if (lCName.equalsIgnoreCase("created date") 
            || lCName.equalsIgnoreCase("created_date") 
            || lCName.equalsIgnoreCase("createddate")) 
        {
            return true;    
        }
        return false;
    }
    
    //DECLARE objects
    public static Element createDatabaseFeature(Document d, String name, String value) {
        Element feature = d.createElement("Feature");
        feature.setAttribute("name", name);
        feature.setAttribute("value", value);
        return feature;
    }
    
    public static Element createDatabaseFeature(Document d, String name) {
        Element feature = d.createElement("Feature");
        feature.setAttribute("name", name);
        
        return feature;
    }
    
    public static List<Element> Oralce11gDatabaseFeatures(Document d) {
        /* there are features for only Oracle 11g */
        List<Element> features = new ArrayList<Element>();
        
        features.add(createDatabaseFeature(d, "LEFT_OUTER_JOIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "RIGHT_OUTER_JOIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "FULL_OUTER_JOIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NESTED_OUTER_JOIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "UNION_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "UNION_ALL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COUNT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COUNT_DISTINCT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COUNT_STAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SUM_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "AVG_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MAX_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "FIRST_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LAST_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "RANK_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PERCENTILE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "MOVING_AVG_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "TOPN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "BOTTOMN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "GROUP_BY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "GROUP_BY_EXPR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "HAVING_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PARAMETERS_IN_HAVING_CLAUSE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ORDERBY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "EXPRESSIONS_IN_ORDERBY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ORDER_BY_COLUMNS_IN_SELECT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "NULL_VALUES_SORT_FIRST", "false"));
        features.add(createDatabaseFeature(d, "DISTINCT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CASE_LOOK_UP_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CASE_IF_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "DATE_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TIME_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "DATE_TIME_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "INTERVAL_LITERAL_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CURDATE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CURTIME_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "NOW_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_DAY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_DAY_OF_QUARTER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_DAY_OF_YEAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_HOUR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_MINUTE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_MONTH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_MONTH_OF_QUARTER_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_QUARTER_OF_YEAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_SECOND_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_WEEK_OF_QUARTER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_WEEK_OF_YEAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_YEAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_WEEK_OF_MONTH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_SHORT_NAME_OF_MONTH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_DAY_OF_WEEK_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_SHORT_NAME_OF_DAY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_DAY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_DAY_OF_QUARTER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_DAY_OF_YEAR_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_HOUR_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_MINUTE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_MONTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_MONTH_OF_QUARTER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_QUARTER_OF_YEAR_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_SECOND_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_WEEK_OF_QUARTER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_WEEK_OF_YEAR_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_YEAR_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_WEEK_OF_MONTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_SHORT_NAME_OF_MONTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_DAY_OF_WEEK_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_SHORT_NAME_OF_DAY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "TIMESTAMP_ADD_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TIMESTAMP_DIFF_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CHAR_LENGTH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "BIT_LENGTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "OCTET_LENGTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "POSITION_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LOCATE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ASCII_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LENGTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "EXP_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "POWER_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SQRT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CEILING_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "FLOOR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ROUND_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TRUNCATE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SIGN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ABS_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MOD_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MODF_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RAND_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "LOG_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LOG10_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PI_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "DEGREES_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RADIANS_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "SIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COS_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ASIN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ACOS_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ATAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ATAN2_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SINH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COSH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TANH_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "TRIM_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LTRIM_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RTRIM_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "UPPER_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LOWER_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CONCAT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SUBSTRING_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "REPLACE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "INSERT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "LEFT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "RIGHT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SPACE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "REPEAT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CHAR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CONCAT_NULL_RETURNS_NULL", "false"));
        features.add(createDatabaseFeature(d, "DATABASE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "IFNULL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "USER_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CONVERT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "NULL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "BETWEEN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LIKE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LIKE_ESCAPE_CLAUSE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "IN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MULTIPLE_STATEMENTS_PER_CONNECTION_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CAST_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ODBC_API_CONFORMANCE_NONE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ODBC_API_CONFORMANCE_LEVEL1_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ODBC_API_CONFORMANCE_LEVEL2 _SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ODBC_SQL_CONFORMANCE_MINIMUM_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ODBC_SQL_CONFORMANCE_CORE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "SQL_ODBC_CONFORMANCE_EXTENDED_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "DERIVED_TABLES_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CORRELATION_NAME_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PREDICATE_SCALAR_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "VALUE_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CORRELATED_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "COMPARISON_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "EXISTS_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "IN_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "QUANTIFIED_SUBQUERY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_IN_LISTS", "true"));
        features.add(createDatabaseFeature(d, "SQL89_COLUMN_ALIAS_CONFORMANCE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ADD_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SUBTRACT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MULTIPLY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "DIVIDE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "UNARY_MINUS_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PARAMETER_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "STRING_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "INTEGER_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "FLOAT_LITERAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "EQUALITY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LESS_THAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "GREATER_THAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "LESS_EQUAL_THAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "GREATER_EQUAL_THAN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NOT_EQUAL_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "AND_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "OR_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NOT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NTILE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "MEDIAN_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "STDDEV_SAMP_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "STDDEV_SAMP_DISTINCT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "STDDEV_POP_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "STDDEV_POP_DISTINCT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_LONG_NAME_OF_MONTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "CALENDAR_EXTRACT_LONG_NAME_OF_DAY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_LONG_NAME_OF_MONTH_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "FISCAL_EXTRACT_LONG_NAME_OF_DAY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "EXCEPT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "EXCEPT_ALL_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "INTERSECT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "INTERSECT_ALL_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RUNNING_SUM_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RUNNING_COUNT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "RUNNING_MAX_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "RUNNING_MIN_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "MOVING_SUM_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "NULLIF_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CREATE_AS_SELECT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "INSERT_SELECT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SUBTOTALLING_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ROWNUM_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "CREATE_VIEW_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "WITH_CLAUSE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_MINIMAL_WITH_USAGE", "false"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_INTERNAL_STITCH_JOIN", "false"));
        features.add(createDatabaseFeature(d, "QUALIFIED_DATA_REFERENCE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "TIME_SERIES_AGO_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "TIME_SERIES_TODATE_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ISDESCENDANT_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ISLEAF_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "GROUP_BY_GROUPING_SETS_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_NO_COMMON_QUERY_FACTORING", "false"));
        features.add(createDatabaseFeature(d, "NESTED_ORDERBY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "LITERAL_IN_PROJECTION_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "DUAL_TABLE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NULLS_SORT_DIRECTION_IN_ORDERBY_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "SUBQUERY_IN_CASE_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_INTERNAL_BREAKFILTER_HANDLING", "false"));
        features.add(createDatabaseFeature(d, "PERF_PREFER_SUPPRESS_EMPTY_TUPLES", "false"));
        features.add(createDatabaseFeature(d, "PREFERS_RTRIM_VARCHAR", "false"));
        features.add(createDatabaseFeature(d, "DENSE_RANK_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "MULTI_COLUMN_IN_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "PREFERS_MDX_RANGE_OPERATOR", "false"));
        features.add(createDatabaseFeature(d, "PREFERS_NATIVE_NULLS_EQUAL_COMPARISON", "true"));
        features.add(createDatabaseFeature(d, "OPTIMIZE_MDX_FILTER_QUALIFICATION", "false"));
        features.add(createDatabaseFeature(d, "COMPRESS_COLUMNS", "false"));
        features.add(createDatabaseFeature(d, "ROWNUM_LIMIT_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "ROWNUM_OFFSET_SUPPORTED", "true"));
        features.add(createDatabaseFeature(d, "NUMERIC_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "ANCESTOR_DIMENSION_PROPERTY_SUPPORTED", "false"));
        features.add(createDatabaseFeature(d, "MAX_BINARY_LITERAL_LEN", "0"));
        features.add(createDatabaseFeature(d, "MAX_CHAR_LITERAL_LEN", "0"));
        features.add(createDatabaseFeature(d, "MAX_COLUMN_NAME_LEN", "0"));
        features.add(createDatabaseFeature(d, "MAX_COLUMNS_IN_GROUP_BY", "1024"));
        features.add(createDatabaseFeature(d, "MAX_COLUMNS_IN_ORDER_BY", "1024"));
        features.add(createDatabaseFeature(d, "MAX_COLUMNS_IN_SELECT", "0"));
        features.add(createDatabaseFeature(d, "MAX_STATEMENT_LEN", "0"));
        features.add(createDatabaseFeature(d, "MAX_TABLES_IN_SELECT", "1024"));
        features.add(createDatabaseFeature(d, "CONVERT_BIG_INT_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_BINARY_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_BIT_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_CHAR_BITMASK", "0X208fe"));
        features.add(createDatabaseFeature(d, "CONVERT_DATE_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_DECIMAL_BITMASK", "0X1"));
        features.add(createDatabaseFeature(d, "CONVERT_DOUBLE_BITMASK", "0X31ff"));
        features.add(createDatabaseFeature(d, "CONVERT_FLOAT_BITMASK", "0X31ff"));
        features.add(createDatabaseFeature(d, "CONVERT_INTEGER_BITMASK", "0X20001"));
        features.add(createDatabaseFeature(d, "CONVERT_LONG_VAR_BINARY_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_LONG_VAR_CHAR_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_NUMERIC_BITMASK", "0X1"));
        features.add(createDatabaseFeature(d, "CONVERT_REAL_BITMASK", "0X1"));
        features.add(createDatabaseFeature(d, "CONVERT_SMALL_INT_BITMASK", "0X20001"));
        features.add(createDatabaseFeature(d, "CONVERT_TIME_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "CONVERT_TIMESTAMP_BITMASK", "0X2019"));
        features.add(createDatabaseFeature(d, "CONVERT_TINY_INT_BITMASK", "0X20001"));
        features.add(createDatabaseFeature(d, "CONVERT_VAR_BINARY_BITMASK", "0X1"));
        features.add(createDatabaseFeature(d, "CONVERT_VAR_CHAR_BITMASK", "0X0"));
        features.add(createDatabaseFeature(d, "FRACTIONAL_SECOND_PRECISION", "0X0"));
        features.add(createDatabaseFeature(d, "MAX_ENTRIES_PER_IN_LIST", "0X3e8"));
        features.add(createDatabaseFeature(d, "MAX_PARAMETERS_PER_DRIVE_JOIN", "0X64"));
        features.add(createDatabaseFeature(d, "MAX_QUERIES_PER_DRIVE_JOIN", "0Xc8"));
        features.add(createDatabaseFeature(d, "MAX_PARAMETERS_PER_BULK_OPERATION", "0X0"));
        features.add(createDatabaseFeature(d, "MAX_NESTED_LEVEL", "0X0"));
        features.add(createDatabaseFeature(d, "MIN_BULK_FETCH_BUFFER_SIZE", "0X8000"));
        features.add(createDatabaseFeature(d, "MAX_BULK_FETCH_BUFFER_SIZE", "0X50000"));
        features.add(createDatabaseFeature(d, "DEFAULT_BULK_FETCH_ROW_COUNT", "0X64"));
        features.add(createDatabaseFeature(d, "PERF_CUSTOM_GROUP_GENERATION_MODE", "0X2"));
        features.add(createDatabaseFeature(d, "DATA_SOURCE_NAME"));
        features.add(createDatabaseFeature(d, "DATABASE_NAME"));
        features.add(createDatabaseFeature(d, "DBMS_NAME"));
        features.add(createDatabaseFeature(d, "DBMS_VER"));
        features.add(createDatabaseFeature(d, "DRIVER_NAME"));
        features.add(createDatabaseFeature(d, "DRIVER_ODBC_VER"));
        features.add(createDatabaseFeature(d, "USER_NAME"));
        features.add(createDatabaseFeature(d, "SEARCH_PATTERN_ESCAPE"));
        features.add(createDatabaseFeature(d, "IDENTIFIER_QUOTE_CHAR"));
        features.add(createDatabaseFeature(d, "DATE_FORMAT"));
        features.add(createDatabaseFeature(d, "TIME_FORMAT"));
        features.add(createDatabaseFeature(d, "DATETIME_FORMAT"));
        features.add(createDatabaseFeature(d, "SORT_ORDER_LOCALE", "english-usa"));
        features.add(createDatabaseFeature(d, "COMMENT_START", "/*"));
        features.add(createDatabaseFeature(d, "COMMENT_END", "*/"));
        features.add(createDatabaseFeature(d, "POPULATE_STATEMENT_INSERT_HINT"));
        
        return features;
    }
    
    public static Element generateDatabase(Document d, String name, String id, String uid, String type) {
        Element database = d.createElement("Database");
        database.setAttribute("name", name);
        database.setAttribute("id", id);
        database.setAttribute("uid", uid);
        database.setAttribute("type", type);
        database.setAttribute("pName", "&quot;" + name + "&quot;");
        
        Element Features = d.createElement("Features");
        for (Element feature : Oralce11gDatabaseFeatures(d)) {
            Features.appendChild(feature);
        }
        database.appendChild(Features);
        
        Element RemovedDisplayFolders = d.createElement("RemovedDisplayFolders");
        database.appendChild(RemovedDisplayFolders);
        
        Element ConnectionPools = d.createElement("ConnectionPools");
        database.appendChild(ConnectionPools);
        
        return database;
    }
    
    /* This method generates only two repository variables
     * @isadikov : Actually, it generates only server static vairables
     * For other sort of modification you need to write a common method for variables initialisation
     */
    public static Element generateRepositoryVariable(Document d, String name, String Expression, String id, String uid) {
        Element Variable = d.createElement("Variable");
        
        Variable.setAttribute("name", name);
        Variable.setAttribute("id", id);
        Variable.setAttribute("uid", uid);
        
        Element Expr = d.createElement("Expr");
        Expr.setTextContent("<![CDATA['" + Expression + "']]>");
        
        Variable.appendChild(Expr);
        Expr = null;
        
        return Variable;
    }
    
    public static Element generateConnectionPool(Document d, String id, String uid, String parentId, String parentUid, String name, String parentName, String user, String password, String dataSource) {
        Element connectionPool = d.createElement("ConnectionPool");
        //required attributes
        connectionPool.setAttribute("name", name);
        connectionPool.setAttribute("user", user);
        connectionPool.setAttribute("password", password);
        connectionPool.setAttribute("dataSource", dataSource);
        
        connectionPool.setAttribute("id", id);
        connectionPool.setAttribute("uid", uid);
        connectionPool.setAttribute("parentId", parentId);
        connectionPool.setAttribute("parentUid", parentUid);
        connectionPool.setAttribute("parentName", parentName);
        connectionPool.setAttribute("pName", parentName + "." + "&quot;" + name + "&quot;");
        
        //common attributes which do not need to be changed
        connectionPool.setAttribute("timeout", "300");
        connectionPool.setAttribute("maxConnDiff", "10");
        connectionPool.setAttribute("maxConn", "10");
        connectionPool.setAttribute("type", "Default");
        connectionPool.setAttribute("reqQualifedTableName", "false");
        connectionPool.setAttribute("isSharedLogin", "true");
        connectionPool.setAttribute("isConcurrentQueriesInConnection", "false");
        connectionPool.setAttribute("isCloseAfterEveryRequest", "false");
        connectionPool.setAttribute("outputType", "xml");
        connectionPool.setAttribute("ignoreFirstLine", "false");
        connectionPool.setAttribute("bulkInsertBufferSize", "131072");
        connectionPool.setAttribute("tempTablePrefix", "TT");
        connectionPool.setAttribute("transactionBoundary", "1000");
        connectionPool.setAttribute("xmlaUseSession", "false");
        connectionPool.setAttribute("isSiebelJDBSecured", "false");
         
        return connectionPool;
    }
    
    public static void setRefConnectionPool(Document d, Element database, Element connectionPool) {
        if (!database.equals(null) && !connectionPool.equals(null)) {
            Element RefConnectionPool = d.createElement("RefConnectionPool");
            RefConnectionPool.setAttribute("id", connectionPool.getAttribute("id"));
            RefConnectionPool.setAttribute("uid", connectionPool.getAttribute("uid"));
            RefConnectionPool.setAttribute("qualifiedName", connectionPool.getAttribute("pName"));
            
            database.getElementsByTagName("ConnectionPools").item(0).appendChild(RefConnectionPool);
        }
    }
    
    public static Element generatePhysicalCatalog(Document d, Element Database, String name, String id, String uid, String description) {
        Element PhysicalCatalog = d.createElement("PhysicalCatalog");
        
        PhysicalCatalog.setAttribute("id", id);
        PhysicalCatalog.setAttribute("uid", uid);
        PhysicalCatalog.setAttribute("name", name);
        PhysicalCatalog.setAttribute("parentId", Database.getAttribute("id"));
        PhysicalCatalog.setAttribute("parentUid", Database.getAttribute("uid"));
        PhysicalCatalog.setAttribute("parentName", Database.getAttribute("pName"));
        PhysicalCatalog.setAttribute("pName", Database.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element Description = d.createElement("Description");
        Description.setTextContent(description);
        PhysicalCatalog.appendChild(Description);
        
        return PhysicalCatalog;
    }
    
    public static Element generateSchema(Document d, Element PhysicalCatalog, String name, String id, String uid) {
        Element Schema = d.createElement("Schema");
        
        Schema.setAttribute("name", name);
        Schema.setAttribute("id", id);
        Schema.setAttribute("uid", uid);
        Schema.setAttribute("parentId", PhysicalCatalog.getAttribute("id"));
        Schema.setAttribute("parentUid", PhysicalCatalog.getAttribute("uid"));
        Schema.setAttribute("parentName", PhysicalCatalog.getAttribute("pName"));
        Schema.setAttribute("pName", PhysicalCatalog.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        return Schema;
    }
    
    public static Element generatePhysicalTable(Document d, Element Schema, String name, String desc, String id, String uid, String dim, String isDummy, String group) {
        Element PhysicalTable = d.createElement("PhysicalTable");
        
        PhysicalTable.setAttribute("name", name);
        PhysicalTable.setAttribute("id", id);
        PhysicalTable.setAttribute("uid", uid);
        PhysicalTable.setAttribute("parentId", Schema.getAttribute("id"));
        PhysicalTable.setAttribute("parentUid", Schema.getAttribute("uid"));
        PhysicalTable.setAttribute("parentName", Schema.getAttribute("pName"));
        PhysicalTable.setAttribute("pName", Schema.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        PhysicalTable.setAttribute("dim", dim);
        //@isadikov: add isDummy tag
        PhysicalTable.setAttribute("isDummy", isDummy);
        //@isadikov: add table description (display name)
        PhysicalTable.setAttribute("dispName", desc);
        //@isadikov: add group name to the table
        PhysicalTable.setAttribute("group", group);
        
        //params for physical table only
        PhysicalTable.setAttribute("type", "none");
        PhysicalTable.setAttribute("isCacheable", "false");
        PhysicalTable.setAttribute("maxConn", "0");
        
        Element Value = d.createElement("Value");
        Value.setTextContent("<![CDATA[" + name + "]]>");
        
        Element Item = d.createElement("Item");
        Item.setAttribute("name", "DefaultMulDB");
        Item.appendChild(Value);
        
        Element DBMap = d.createElement("DBMap");
        DBMap.appendChild(Item);
        
        Element XSLT = d.createElement("XSLT");
        Element XSDSchema = d.createElement("XSDSchema");
        
        PhysicalTable.appendChild(DBMap);
        PhysicalTable.appendChild(XSLT);
        PhysicalTable.appendChild(XSDSchema);
        
        return PhysicalTable;
    }
    
    public static Element generatePhysicalColumn(Document d, Element PhysicalTable, String name, String id, String uid, String dataType, String precision, String nullable, String aggrRule, String isPrimaryKey, String isForeignKey, String dispName) {
        Element PhysicalColumn = d.createElement("PhysicalColumn");
        
        PhysicalColumn.setAttribute("name", name);
        PhysicalColumn.setAttribute("dispName", dispName);
        PhysicalColumn.setAttribute("id", id);
        PhysicalColumn.setAttribute("uid", uid);
        PhysicalColumn.setAttribute("parentId", PhysicalTable.getAttribute("id"));
        PhysicalColumn.setAttribute("parentUid", PhysicalTable.getAttribute("uid"));
        PhysicalColumn.setAttribute("parentName", PhysicalTable.getAttribute("pName"));
        
        PhysicalColumn.setAttribute("pName", PhysicalTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        PhysicalColumn.setAttribute("aggrType", aggrRule);
        PhysicalColumn.setAttribute("isPrimaryKey", isPrimaryKey);
        PhysicalColumn.setAttribute("isForeignKey", isForeignKey);
        
        PhysicalColumn.setAttribute("dataType", dataType);
        PhysicalColumn.setAttribute("precision", precision);
        PhysicalColumn.setAttribute("nullable", nullable);
        
        PhysicalColumn.setAttribute("specialType", "none");
        
        return PhysicalColumn;
    }
    
    public static Element generatePhysicalKey(Document d, Element PhysicalTable, Element PhysicalColumn, String id, String uid, String name) {
        Element PhysicalKey = d.createElement("PhysicalKey");
        
        PhysicalKey.setAttribute("name", name);
        PhysicalKey.setAttribute("id", id);
        PhysicalKey.setAttribute("uid", uid);
        PhysicalKey.setAttribute("parentId", PhysicalTable.getAttribute("id"));
        PhysicalKey.setAttribute("parentUid", PhysicalTable.getAttribute("uid"));
        PhysicalKey.setAttribute("parentName", PhysicalTable.getAttribute("pName"));
        
        PhysicalKey.setAttribute("pName", PhysicalTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element RefPhysicalColumn = d.createElement("RefPhysicalColumn");
        
        RefPhysicalColumn.setAttribute("id", PhysicalColumn.getAttribute("id"));
        RefPhysicalColumn.setAttribute("uid", PhysicalColumn.getAttribute("uid"));
        RefPhysicalColumn.setAttribute("qualifiedName", PhysicalColumn.getAttribute("pName"));
        
        Element Columns = d.createElement("Columns");
        Columns.appendChild(RefPhysicalColumn);
        
        PhysicalKey.appendChild(Columns);
        
        return PhysicalKey;
    }
    
    public static Element generatePhysicalForeignKey(Document d, Element PhysicalTable, Element PhysicalColumn, Element PhysicalKey, String name, String id, String uid) {
        Element PhysicalForeignKey = d.createElement("PhysicalForeignKey");
        
        PhysicalForeignKey.setAttribute("name", name);
        PhysicalForeignKey.setAttribute("id", id);
        PhysicalForeignKey.setAttribute("uid", uid);
        PhysicalForeignKey.setAttribute("parentName", PhysicalTable.getAttribute("pName"));
        PhysicalForeignKey.setAttribute("parentId", PhysicalTable.getAttribute("id"));
        PhysicalForeignKey.setAttribute("parentUid", PhysicalTable.getAttribute("uid"));
        
        PhysicalForeignKey.setAttribute("pName", PhysicalTable.getAttribute("pName") + "&quot;" + name + "&quot;");
        
        Element RefPhysicalColumn = d.createElement("RefPhysicalColumn");
        
        RefPhysicalColumn.setAttribute("id", PhysicalColumn.getAttribute("id"));
        RefPhysicalColumn.setAttribute("uid", PhysicalColumn.getAttribute("uid"));
        RefPhysicalColumn.setAttribute("qualifiedName", PhysicalColumn.getAttribute("pName"));
        
        Element Columns = d.createElement("Columns");
        Columns.appendChild(RefPhysicalColumn);
        
        Element RefPhysicalKey = d.createElement("RefPhysicalKey");
        RefPhysicalKey.setAttribute("id", PhysicalKey.getAttribute("id"));
        RefPhysicalKey.setAttribute("uid", PhysicalKey.getAttribute("uid"));
        RefPhysicalKey.setAttribute("qualifiedName", PhysicalKey.getAttribute("pName"));
        
        Element CounterPartKey = d.createElement("CounterPartKey");
        CounterPartKey.appendChild(RefPhysicalKey);
        
        PhysicalForeignKey.appendChild(Columns);
        PhysicalForeignKey.appendChild(CounterPartKey);
        
        return PhysicalForeignKey;
    }
    
    public static Element generateBusinessModel(Document d, String name, String id, String uid) {
        Element BusinessModel = d.createElement("BusinessModel");
        
        BusinessModel.setAttribute("name", name);
        BusinessModel.setAttribute("id", id);
        BusinessModel.setAttribute("uid", uid);
        BusinessModel.setAttribute("isClassicStar", "true");
        BusinessModel.setAttribute("isAvailable", "true");
        BusinessModel.setAttribute("pName", "&quot;" + name + "&quot;");
        
        Element Description = d.createElement("Description");
        Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        BusinessModel.appendChild(Description);
        
        return BusinessModel;
    }
    
    public static Element generateLogicalTable(Document d, Element BusinessModel, String name, String desc, String id, String uid, String dim, String isDummy, String group) {
        Element LogicalTable = d.createElement("LogicalTable");
        
        LogicalTable.setAttribute("name", name);
        LogicalTable.setAttribute("id", id);
        LogicalTable.setAttribute("uid", uid);
        LogicalTable.setAttribute("parentName", BusinessModel.getAttribute("pName"));
        LogicalTable.setAttribute("parentId", BusinessModel.getAttribute("id"));
        LogicalTable.setAttribute("parentUid", BusinessModel.getAttribute("uid"));
        LogicalTable.setAttribute("pName", BusinessModel.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        LogicalTable.setAttribute("dim", dim);
        //@isadikov: add isDummy tag
        LogicalTable.setAttribute("isDummy", isDummy);
        LogicalTable.setAttribute("dispName", desc);
        //@isadikov: add group name to the logical table
        LogicalTable.setAttribute("group", group);
        
        Element Description = d.createElement("Description");
        Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        
        Element Columns = d.createElement("Columns");
        Element TableSources = d.createElement("TableSources");
        
        LogicalTable.appendChild(Description);
        LogicalTable.appendChild(Columns);
        LogicalTable.appendChild(TableSources);
        
        return LogicalTable;
    }
    
    public static Element generateLogicalColumn(Document d, Element LogicalTable, String name, String id, String uid, String aggrType, String isPrimaryKey, String isForeignKey, String dispName) {
        Element LogicalColumn = d.createElement("LogicalColumn");
        
        LogicalColumn.setAttribute("name", name);
        LogicalColumn.setAttribute("dispName", dispName);
        LogicalColumn.setAttribute("id", id);
        LogicalColumn.setAttribute("uid", uid);
        LogicalColumn.setAttribute("aggrType", aggrType);
        LogicalColumn.setAttribute("isWriteable", "false");
        
        LogicalColumn.setAttribute("isPrimaryKey", isPrimaryKey);
        LogicalColumn.setAttribute("isForeignKey", isForeignKey);
        
        LogicalColumn.setAttribute("parentName", LogicalTable.getAttribute("pName"));
        LogicalColumn.setAttribute("parentId", LogicalTable.getAttribute("id"));
        LogicalColumn.setAttribute("parentUid", LogicalTable.getAttribute("uid"));
        
        LogicalColumn.setAttribute("pName", LogicalTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element Description = d.createElement("Description");
        Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        LogicalColumn.appendChild(Description);
        
        
        return LogicalColumn;
    }
    
    public static void setRefLogicalColumn(Document d, Element LogicalTable, Element LogicalColumn) {
        Element RefLogicalColumn = d.createElement("RefLogicalColumn");
        
        RefLogicalColumn.setAttribute("id", LogicalColumn.getAttribute("id"));
        RefLogicalColumn.setAttribute("uid", LogicalColumn.getAttribute("uid"));
        RefLogicalColumn.setAttribute("qualifiedName", LogicalColumn.getAttribute("pName"));
        
        LogicalTable.getElementsByTagName("Columns").item(0).appendChild(RefLogicalColumn);
    }
    
    public static void setLogicalColumnCustomSortOrder(Document d, Element LogicalColumn, Element LogicalSortColumn) {
        Element RefLogicalColumn = d.createElement("RefLogicalColumn");
        
        RefLogicalColumn.setAttribute("id", LogicalSortColumn.getAttribute("id"));
        RefLogicalColumn.setAttribute("uid", LogicalSortColumn.getAttribute("uid"));
        RefLogicalColumn.setAttribute("qualifiedName", LogicalSortColumn.getAttribute("pName"));
        
        Element CustomSortOrder = d.createElement("CustomSortOrder");
        CustomSortOrder.appendChild(RefLogicalColumn);
        
        LogicalColumn.appendChild(CustomSortOrder);
    }
    
    public static Element generateColumnMapping(Document d, Element LogicalTableSource, Element LogicalColumn, Element PhysicalColumn) {
        Element ColumnMapping = d.createElement("ColumnMapping");
        
        Element RefLogicalTableSource = d.createElement("RefLogicalTableSource");
        
        RefLogicalTableSource.setAttribute("id", LogicalTableSource.getAttribute("id"));
        RefLogicalTableSource.setAttribute("uid", LogicalTableSource.getAttribute("uid"));
        RefLogicalTableSource.setAttribute("qualifiedName", LogicalTableSource.getAttribute("pName"));
        
        Element LogicalColumnNodeElement = d.createElement("LogicalColumn");
        Element lExpr = d.createElement("Expr");
        
        //set logical column expression
        lExpr.setTextContent("<![CDATA[" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + "]]>");
        LogicalColumnNodeElement.appendChild(lExpr);
        
        //set physical column expression
        Element Expr = d.createElement("Expr");
        Expr.setTextContent("<![CDATA[" + PhysicalColumn.getAttribute("pName").replace("&quot;", "\"") + "]]>");
        
        ColumnMapping.appendChild(RefLogicalTableSource);
        ColumnMapping.appendChild(LogicalColumnNodeElement);
        ColumnMapping.appendChild(Expr);
        
        return ColumnMapping;
    }
    
    public static Element generateLogicalTableSource(Document d, Element LogicalTable, Element PhysicalTable, List<Element> PhysicalTables, List<Element> PhysicalForeignKeys, List<Element> PhysicalKeys, String name, String id, String uid, String[] arrayForComplexTSource) {
        Element LogicalTableSource = d.createElement("LogicalTableSource");
        
        LogicalTableSource.setAttribute("name", name);
        LogicalTableSource.setAttribute("id", id);
        LogicalTableSource.setAttribute("uid", uid);
        
        LogicalTableSource.setAttribute("parentName", LogicalTable.getAttribute("pName"));
        LogicalTableSource.setAttribute("parentId", LogicalTable.getAttribute("id"));
        LogicalTableSource.setAttribute("parentUid", LogicalTable.getAttribute("uid"));
        
        LogicalTableSource.setAttribute("pName", LogicalTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        LogicalTableSource.setAttribute("isActive", "true");
        
        //reference
        //@isadikov: if we encounter table "fact" we need to add all dimensional tables to fact source.
        // so we need to define Link element differently for tables
        // that's not entirely true. We use complex tables source only when it is required by business
        // [it needs to modify actually]
        // we use branch 2 for tables in the method if tables types are declared in "arrayForComplexTSource", 
        // if not, we use branch 1
        /***/
        // branch 1
        /***/
        if (!Common.contains(arrayForComplexTSource, LogicalTable.getAttribute("dim"))) {
        
            Element RefPhysicalTable = d.createElement("RefPhysicalTable");
            RefPhysicalTable.setAttribute("id", PhysicalTable.getAttribute("id"));
            RefPhysicalTable.setAttribute("uid", PhysicalTable.getAttribute("uid"));
            RefPhysicalTable.setAttribute("qualifiedName", PhysicalTable.getAttribute("pName"));
            
            Element StartNode = d.createElement("StartNode");
            StartNode.appendChild(RefPhysicalTable);
            
            Element Link = d.createElement("Link");
            Link.appendChild(StartNode);
            
            //add to main element "LogicalTableSource"
            LogicalTableSource.appendChild(Link);
    
        /***/
        // branch 2
        /***/
        } else {
            List<Element> Links = new ArrayList<Element>();
            List<Element> FactJoins = new ArrayList<Element>();
            
            // create global Fact ref element(or parent dim if is set in arrayForComplexTSource)
            Element RefPhysicalTable = d.createElement("RefPhysicalTable");
            RefPhysicalTable.setAttribute("id", PhysicalTable.getAttribute("id"));
            RefPhysicalTable.setAttribute("uid", PhysicalTable.getAttribute("uid"));
            RefPhysicalTable.setAttribute("qualifiedName", PhysicalTable.getAttribute("pName"));
            // create link for start node fact
            Element StartNodeFact = d.createElement("StartNode");
            StartNodeFact.appendChild(RefPhysicalTable);
            
            Element JoinsFact = d.createElement("Joins");
            
            
            for (Element fKey : PhysicalForeignKeys) {
                
                if (fKey.getAttribute("parentId").equals(PhysicalTable.getAttribute("id")) && fKey.getAttribute("parentUid").equals(PhysicalTable.getAttribute("uid"))) {
                    
                    Element CounterPartKey = (Element)(fKey.getElementsByTagName("CounterPartKey").item(0));
                    Element RefPhysicalKey = (Element)(CounterPartKey.getElementsByTagName("RefPhysicalKey").item(0));
                    for (Element key : PhysicalKeys) {
                        if (key.getAttribute("id").equals(RefPhysicalKey.getAttribute("id")) && key.getAttribute("uid").equals(RefPhysicalKey.getAttribute("uid"))) {
                            for (Element pTable : PhysicalTables) {
                                if (pTable.getAttribute("id").equals(key.getAttribute("parentId")) && pTable.getAttribute("uid").equals(key.getAttribute("parentUid"))) {
                                    // create instances for ref objects
                                    // 1. Attr
                                    Element RefPhysicalTableAttr = d.createElement("RefPhysicalTable");
                                    RefPhysicalTableAttr.setAttribute("id", pTable.getAttribute("id"));
                                    RefPhysicalTableAttr.setAttribute("uid", pTable.getAttribute("uid"));
                                    RefPhysicalTableAttr.setAttribute("qualifiedName", pTable.getAttribute("pName"));
                                    // 2. Fact (or parent dim if is set in arrayForComplexTSource)
                                    Element RefPhysicalTableFact = d.createElement("RefPhysicalTable");
                                    RefPhysicalTableFact.setAttribute("id", PhysicalTable.getAttribute("id"));
                                    RefPhysicalTableFact.setAttribute("uid", PhysicalTable.getAttribute("uid"));
                                    RefPhysicalTableFact.setAttribute("qualifiedName", PhysicalTable.getAttribute("pName"));
                                    // 3. Foreign Key
                                    Element RefPhysicalForeignKey = d.createElement("RefPhysicalForeignKey");
                                    RefPhysicalForeignKey.setAttribute("id", fKey.getAttribute("id"));
                                    RefPhysicalForeignKey.setAttribute("uid", fKey.getAttribute("uid"));
                                    RefPhysicalForeignKey.setAttribute("qualifiedName", fKey.getAttribute("pName"));
                                    
                                    // @isadikov: first we create fact table ref to recognize it 
                                    // when we would be searching for table reference in logical complex join
                                    
                                    // create link for start node attr
                                    Element StartNodeAttr = d.createElement("StartNode");
                                    StartNodeAttr.appendChild(RefPhysicalTableAttr);
                                    
                                    Element JoinAttr = d.createElement("Join");
                                    
                                    JoinAttr.setAttribute("type", "Inner");
                                    JoinAttr.setAttribute("cardinality", " ONE TO MANY");
                                    // make clones for objects to use
                                    Element RefPhysicalTableAttrClone1 = (Element)RefPhysicalTableAttr.cloneNode(true);
                                    Element RefPhysicalTableFactClone1 = (Element)RefPhysicalTableFact.cloneNode(true);
                                    Element RefPhysicalForeignKeyClone1 = (Element)RefPhysicalForeignKey.cloneNode(true);
                                    
                                    JoinAttr.appendChild(RefPhysicalTableAttrClone1);
                                    JoinAttr.appendChild(RefPhysicalTableFactClone1);
                                    JoinAttr.appendChild(RefPhysicalForeignKeyClone1);
                                    
                                    Element JoinsAttr = d.createElement("Joins");
                                    JoinsAttr.appendChild(JoinAttr);
                                    
                                    // create temp Link element
                                    Element tempLink1 = d.createElement("Link");
                                    tempLink1.appendChild(StartNodeAttr);
                                    tempLink1.appendChild(JoinsAttr);
                                    
                                    Links.add(tempLink1);
                                    
                                    tempLink1 = null;
                                    

                                    // create current join element for dim and fact table
                                    Element JoinFact = d.createElement("Join");
                                    // make clones for objects to use
                                    Element RefPhysicalTableAttrClone2 = (Element)RefPhysicalTableAttr.cloneNode(true);
                                    Element RefPhysicalTableFactClone2 = (Element)RefPhysicalTableFact.cloneNode(true);
                                    Element RefPhysicalForeignKeyClone2 = (Element)RefPhysicalForeignKey.cloneNode(true);
                                    
                                    JoinFact.setAttribute("type", "Inner");
                                    JoinFact.setAttribute("cardinality", "MANY TO ONE");
                                    JoinFact.appendChild(RefPhysicalTableFactClone2);
                                    JoinFact.appendChild(RefPhysicalTableAttrClone2);
                                    JoinFact.appendChild(RefPhysicalForeignKeyClone2);
                                    
                                    FactJoins.add(JoinFact);
                                    JoinFact = null;
                                }
                            }
                        }
                    }
                    CounterPartKey = null;
                    RefPhysicalKey = null;
                }
            }
            
            for (Element join : FactJoins) {
                JoinsFact.appendChild(join);
            }
            
            Element linkFact = d.createElement("Link");
            linkFact.appendChild(StartNodeFact);
            linkFact.appendChild(JoinsFact);
            
            Links.add(0, linkFact);
            
            for (Element link : Links) {
                LogicalTableSource.appendChild(link);
            }
            Links = null;
        }
        
        Element Expr = d.createElement("Expr");
        Element WhereClause = d.createElement("WhereClause");
        WhereClause.appendChild(Expr);
        
        Element GroupBy = d.createElement("GroupBy");
        GroupBy.appendChild(Expr);
        
        Element FragmentContent = d.createElement("FragmentContent");
        FragmentContent.appendChild(Expr);
        
        LogicalTableSource.appendChild(WhereClause);
        LogicalTableSource.appendChild(GroupBy);
        LogicalTableSource.appendChild(FragmentContent);
        
        return LogicalTableSource;
    }
    
    public static void setRefLogicalTableSource(Document d, Element LogicalTable, Element LogicalTableSource) {
        Element RefLogicalTableSource = d.createElement("RefLogicalTableSource");
        
        RefLogicalTableSource.setAttribute("id", LogicalTableSource.getAttribute("id"));
        RefLogicalTableSource.setAttribute("uid", LogicalTableSource.getAttribute("uid"));
        RefLogicalTableSource.setAttribute("qualifiedName", LogicalTableSource.getAttribute("pName"));
        
        LogicalTable.getElementsByTagName("TableSources").item(0).appendChild(RefLogicalTableSource);
    }
        
    public static Element generateLogicalKey(Document d, Element LogicalTable, Element LogicalColumn, String name, String id, String uid) {
        Element LogicalKey = d.createElement("LogicalKey");
        
        LogicalKey.setAttribute("id", id);
        LogicalKey.setAttribute("uid", uid);
        LogicalKey.setAttribute("name", name);
        LogicalKey.setAttribute("isPrimary", "true");
        
        LogicalKey.setAttribute("parentName", LogicalTable.getAttribute("pName"));
        LogicalKey.setAttribute("parentId", LogicalTable.getAttribute("id"));
        LogicalKey.setAttribute("parentUid", LogicalTable.getAttribute("uid"));
        LogicalKey.setAttribute("pName", LogicalTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element Columns = d.createElement("Columns");
        Element RefLogicalColumn = d.createElement("RefLogicalColumn");
        RefLogicalColumn.setAttribute("id", LogicalColumn.getAttribute("id"));
        RefLogicalColumn.setAttribute("uid", LogicalColumn.getAttribute("uid"));
        RefLogicalColumn.setAttribute("qualifiedName", LogicalColumn.getAttribute("pName"));
        
        Columns.appendChild(RefLogicalColumn);
        LogicalKey.appendChild(Columns);
        
        return LogicalKey;
    }
    
    public static Element generateMeasureDfn(Document d, Element LogicalColumn, String id, String uid, String name) {
        Element MeasureDefn = d.createElement("MeasureDefn");
        
        MeasureDefn.setAttribute("name", name);
        MeasureDefn.setAttribute("id", id);
        MeasureDefn.setAttribute("uid", uid);
        MeasureDefn.setAttribute("isCommutative", "false");
        
        MeasureDefn.setAttribute("parentName", LogicalColumn.getAttribute("pName"));
        MeasureDefn.setAttribute("parentId", LogicalColumn.getAttribute("id"));
        MeasureDefn.setAttribute("parentUid", LogicalColumn.getAttribute("uid"));
        
        MeasureDefn.setAttribute("pName", LogicalColumn.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element Rules = d.createElement("Rules");
        MeasureDefn.appendChild(Rules);
        Rules = null;
        
        return MeasureDefn;
    }
    
    public static Element generateAggrRule(Document d, Element LogicalColumn, Element MeasureDfn, String id, String uid) {
        Element AggrRule = d.createElement("AggrRule");
        
        String name = "AggRule_" + id + IDGen.generatePrfx(10000, 99999) + "";
        
        AggrRule.setAttribute("name", name);
        AggrRule.setAttribute("id", id);
        AggrRule.setAttribute("uid", uid);
        AggrRule.setAttribute("isDefault", "true");
        
        AggrRule.setAttribute("parentName", MeasureDfn.getAttribute("pName"));
        AggrRule.setAttribute("parentId", MeasureDfn.getAttribute("id"));
        AggrRule.setAttribute("parentUid", MeasureDfn.getAttribute("uid"));
        
        AggrRule.setAttribute("pName", MeasureDfn.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        name = null;
        
        Element Expr = d.createElement("Expr");
        
        String aggrType = LogicalColumn.getAttribute("aggrType");
        if (aggrType.equals("sum")) {
            Expr.setTextContent("<![CDATA[ " + "SUM" + "(" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + ")" + "]]>");
        } else if (aggrType.equals("min")) {
            Expr.setTextContent("<![CDATA[ " + "MIN" + "(" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + ")" + "]]>");
        } else if (aggrType.equals("max")) {
            Expr.setTextContent("<![CDATA[ " + "MAX" + "(" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + ")" + "]]>");
        } else if (aggrType.equals("avg")) {
            Expr.setTextContent("<![CDATA[ " + "AVG" + "(" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + ")" + "]]>");
        } else if (aggrType.equals("count")) {
            Expr.setTextContent("<![CDATA[ " + "COUNT" + "(" + LogicalColumn.getAttribute("pName").replace("&quot;", "\"") + ")" + "]]>"); 
        } else {
            Expr.setTextContent("");
        }
        
        AggrRule.appendChild(Expr);
        Expr = null;
        
        return AggrRule;
    }
    
    public static void setRefAggrRule(Document d, Element MeasureDfn, Element AggrRule) {
        Element RefAggrRule = d.createElement("RefAggrRule");
        
        RefAggrRule.setAttribute("id", AggrRule.getAttribute("id"));
        RefAggrRule.setAttribute("uid", AggrRule.getAttribute("uid"));
        RefAggrRule.setAttribute("qualifiedName", AggrRule.getAttribute("pName"));
        
        Element Rules = (Element)MeasureDfn.getElementsByTagName("Rules").item(0);
        Rules.appendChild(RefAggrRule);
    }
    
    public static Element generateLogicalComplexJoin(Document d, String id, String uid, Element joiningFactTable, Element joiningDimTable, String joinType) {
        Element LogicalComplexJoin = d.createElement("LogicalComplexJoin");
        
        LogicalComplexJoin.setAttribute("name", "Relationship_" + id + IDGen.generatePrfx(10000, 99999));
        LogicalComplexJoin.setAttribute("id", id);
        LogicalComplexJoin.setAttribute("uid", uid);
        LogicalComplexJoin.setAttribute("type", joinType);
        
        Element Roles = d.createElement("Roles");
        
        // Join between two tables: "fact" and "dim" tables or between "parent_dim" and "dim" tables
        // First we look for fact table (parent table). It can be "fact" or "parent_dim" table.
        // So, we create Element parentTable, Element parentCurRefLogicalTable, Element parentRole
        //1. set parent table (fact or parent_dim)
        Element parentTable = joiningFactTable;
        //2. set parent ref logical table
        Element parentCurRefLogicalTable = d.createElement("RefLogicalTable");
        
        parentCurRefLogicalTable.setAttribute("id", parentTable.getAttribute("id"));
        parentCurRefLogicalTable.setAttribute("uid", parentTable.getAttribute("uid"));
        parentCurRefLogicalTable.setAttribute("qualifiedName", parentTable.getAttribute("pName"));
        
        //3. set parent role in the join with parameters
        Element parentRole = d.createElement("Role");
        // @isadikov: we firstly changed multiplicity on "n..n", then "0..n"
        // maybe, "0..n" - is set for tables when tableSource is complex (a lot of tables and joins inside)
        // (see branch 2 in generateLogicalTableSource() method)
        // "n..n" - is set for tables when tableSource contains only fact table
        parentRole.setAttribute("multiplicity", "n..n");
        parentRole.setAttribute("isAggregate", "false");
        parentRole.appendChild(parentCurRefLogicalTable);
            
        // Second we look for child table (it can be "parent_dim" or "dim" tables only).
        // We also create Element childTable, Element childCurRefLogicalTable, Element childRole
        //1. set up child table (dim or parent_dim)
        Element childTable = joiningDimTable;
        //2. set child ref logical table (just like table source)
        Element childCurRefLogicalTable = d.createElement("RefLogicalTable");
        
        childCurRefLogicalTable.setAttribute("id", childTable.getAttribute("id"));
        childCurRefLogicalTable.setAttribute("uid", childTable.getAttribute("uid"));
        childCurRefLogicalTable.setAttribute("qualifiedName", childTable.getAttribute("pName"));
        
        //3. set child role in the join with parameters
        Element childRole = d.createElement("Role");
        childRole.setAttribute("multiplicity", "0..1");
        childRole.setAttribute("isAggregate", "false");
        childRole.appendChild(childCurRefLogicalTable);
        
        // The next move is to add each role to Roles element.
        // @isadikov: tables order changed because of error while user creates analysis
        Roles.appendChild(childRole);
        Roles.appendChild(parentRole);
        // Finally we add Roles element to the main LogicalComplexJoin element.
        LogicalComplexJoin.appendChild(Roles);
        
        // set variables to null
        parentCurRefLogicalTable = null;
        childCurRefLogicalTable = null;
        parentRole = null;
        childRole = null;
        Roles = null;
        
        return LogicalComplexJoin;
    }
    
    public static Element generatePresentationCatalog(Document d, Element BusinessModel, String name, String dispName, String desc, String id, String uid) {
        Element PresentationCatalog = d.createElement("PresentationCatalog");
        
        PresentationCatalog.setAttribute("name", name);
        PresentationCatalog.setAttribute("dispName", dispName);
        PresentationCatalog.setAttribute("id", id);
        PresentationCatalog.setAttribute("uid", uid);
        PresentationCatalog.setAttribute("hasDispName", "true");
        PresentationCatalog.setAttribute("hasDispDescription", "true");
        PresentationCatalog.setAttribute("hasDispDescription", "true");
        PresentationCatalog.setAttribute("pName", "&quot;" + name + "&quot;");
        
        Element RefBusinessModel = d.createElement("RefBusinessModel");
        RefBusinessModel.setAttribute("id", BusinessModel.getAttribute("id"));
        RefBusinessModel.setAttribute("uid", BusinessModel.getAttribute("uid"));
        RefBusinessModel.setAttribute("qualifiedName", BusinessModel.getAttribute("pName"));
        
        Element Description = d.createElement("Description");
        if (desc.length() <= 0 || desc == null) {
            Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        } else {
            if (Common.hasUnsufficientSymbol(desc)) {
                Description.setTextContent("<![CDATA[" + desc + "]]>");
            } else {
                Description.setTextContent(desc);
            }
        }
        
        Element Aliases = d.createElement("Aliases");
        
        Element Tables = d.createElement("Tables");
        
        Element RemovedPresentationTables = d.createElement("RemovedPresentationTables");
        
        PresentationCatalog.appendChild(Description);
        PresentationCatalog.appendChild(RefBusinessModel);
        PresentationCatalog.appendChild(Aliases);
        PresentationCatalog.appendChild(Tables);
        PresentationCatalog.appendChild(RemovedPresentationTables);
        
        return PresentationCatalog;
    }
    
    public static Element generatePresentationTable(Document d, Element PresentationCatalog, String name, String id, String uid, String dispName, String group) {
        Element PresentationTable = d.createElement("PresentationTable");
        
        PresentationTable.setAttribute("name", name);
        PresentationTable.setAttribute("id", id);
        PresentationTable.setAttribute("uid", uid);
        PresentationTable.setAttribute("dispName", ((dispName.length()>0)?dispName : name));
        PresentationTable.setAttribute("hasDispName", "true");
        PresentationTable.setAttribute("hasDispDescription", "false");
        //@isadikov: add table group to the presentation table
        PresentationTable.setAttribute("group", group);
        
        PresentationTable.setAttribute("parentId", PresentationCatalog.getAttribute("id"));
        PresentationTable.setAttribute("parentUid", PresentationCatalog.getAttribute("uid"));
        PresentationTable.setAttribute("parentName", PresentationCatalog.getAttribute("pName") + ".&quot;&quot;");
        
        PresentationTable.setAttribute("pName", PresentationCatalog.getAttribute("pName") + "..&quot;" + name + "&quot;");
        
        Element Description = d.createElement("Description");
        Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        PresentationTable.appendChild(Description);
        
        Element Columns = d.createElement("Columns");
        
        Element RemovedPresentationColumns = d.createElement("RemovedPresentationColumns");
        
        PresentationTable.appendChild(Columns);
        PresentationTable.appendChild(RemovedPresentationColumns);
        
        return PresentationTable;
    }
    
    public static void setRefPresentationTable(Document d, Element PresentationCatalog, Element PresentationTable) {
        Element RefPresentationTable = d.createElement("RefPresentationTable");
        
        RefPresentationTable.setAttribute("id", PresentationTable.getAttribute("id"));
        RefPresentationTable.setAttribute("uid", PresentationTable.getAttribute("uid"));
        RefPresentationTable.setAttribute("qualifiedName", PresentationTable.getAttribute("pName"));
        
        Element tables = (Element)PresentationCatalog.getElementsByTagName("Tables").item(0);
        tables.appendChild(RefPresentationTable);
    }
    
    public static Element generatePresentationGroupTable(Document d, Element PresentationCatalog, String currentGroupName, String id, String uid, String groupDispName, String group) {
        Element PresentationGroupTable = generatePresentationTable(d, PresentationCatalog, currentGroupName, id, uid, groupDispName, group);
        
        if (!PresentationGroupTable.getElementsByTagName("Columns").item(0).hasChildNodes()) {
            PresentationGroupTable.removeChild(PresentationGroupTable.getElementsByTagName("Columns").item(0));
            PresentationGroupTable.removeChild(PresentationGroupTable.getElementsByTagName("RemovedPresentationColumns").item(0));
        }
        
        return PresentationGroupTable;
    }
    
    public static boolean checkPresentationTableGroup(Element PresentationGroupTable, Element PresentationTable) {
        String curGroup = PresentationTable.getAttribute("group");
        
        if (PresentationGroupTable.getAttribute("group").equals(curGroup)) {
            return true;
        } else {
            return false;
        }
        
    }
    
    public static void setRefPresentationGroupTable(Document d, Element PresentationGroupTable, Element PresentationTable) {
        Element RefPresentationTable = d.createElement("RefPresentationTable");
        
        RefPresentationTable.setAttribute("id", PresentationTable.getAttribute("id"));
        RefPresentationTable.setAttribute("uid", PresentationTable.getAttribute("uid"));
        RefPresentationTable.setAttribute("qualifiedName", PresentationTable.getAttribute("pName"));
        
        if (PresentationGroupTable.getElementsByTagName("ContentFolders").getLength() > 0) {
            Element contentFolders = (Element)PresentationGroupTable.getElementsByTagName("ContentFolders").item(0);
            contentFolders.appendChild(RefPresentationTable);
            
            contentFolders = null;
        } else {
            Element contentFolders = d.createElement("ContentFolders");
            contentFolders.appendChild(RefPresentationTable);
            PresentationGroupTable.appendChild(contentFolders);
            
            contentFolders = null;
        }
    }
    
    public static Element generatePresentationColumn(Document d, Element PresentationTable, Element LogicalColumn, String name, String id, String uid, String dispName) {
        Element PresentationColumn = d.createElement("PresentationColumn");
        
        PresentationColumn.setAttribute("name", name);
        PresentationColumn.setAttribute("id", id);
        PresentationColumn.setAttribute("uid", uid);
        PresentationColumn.setAttribute("hasDispName", "true");
        PresentationColumn.setAttribute("dispName", ((dispName.length() > 0)?dispName : name));
        PresentationColumn.setAttribute("hasDispDescription", "false");
        PresentationColumn.setAttribute("overrideLogicalName", "false");
        
        PresentationColumn.setAttribute("parentId", PresentationTable.getAttribute("id"));
        PresentationColumn.setAttribute("parentUid", PresentationTable.getAttribute("uid"));
        PresentationColumn.setAttribute("parentName", PresentationTable.getAttribute("pName"));
        
        PresentationColumn.setAttribute("pName", PresentationTable.getAttribute("pName") + "." + "&quot;" + name + "&quot;");
        
        Element Description = d.createElement("Description");
        Description.setTextContent("<![CDATA[This instance was added using the XML metadata API.]]>");
        
        PresentationColumn.appendChild(Description);
        
        Element RefLogicalColumn = d.createElement("RefLogicalColumn");
        RefLogicalColumn.setAttribute("id", LogicalColumn.getAttribute("id"));
        RefLogicalColumn.setAttribute("uid", LogicalColumn.getAttribute("uid"));
        RefLogicalColumn.setAttribute("qualifiedName", LogicalColumn.getAttribute("pName"));
        
        PresentationColumn.appendChild(RefLogicalColumn);
        
        return PresentationColumn;
    }
    
    public static void setRefPresentationColumn(Document d, Element PresentationTable, Element PresentationColumn) {
        Element RefPresentationColumn = d.createElement("RefPresentationColumn");
        
        RefPresentationColumn.setAttribute("id", PresentationColumn.getAttribute("id"));
        RefPresentationColumn.setAttribute("uid", PresentationColumn.getAttribute("uid"));
        RefPresentationColumn.setAttribute("qualifiedName", PresentationColumn.getAttribute("pName"));
        
        Element Columns = (Element)PresentationTable.getElementsByTagName("Columns").item(0);
        Columns.appendChild(RefPresentationColumn);
    }
    
    public static Document getRepositoryChangesXML(Document d, String pmName, String nextModelNumber, String pmDesc, List<HashMap> tablesData, List<String> tablesGroupsData, boolean showFullStructure, boolean showGroupFolders) {
        if (d == null) {
            d = Common.generateDom();
        }
        
        Element declare = (Element)d.getDocumentElement().getElementsByTagName("DECLARE").item(0);
        String dbName = "DB" + nextModelNumber + "_" + pmName;
        String cpName = "CP_" + dbName;
        String catalogName = "CTLG_DB_" + pmName;
        String schemaName = Consts.getDB().DB_SCHEME_NAME;
        int tableIndex = 0;
        int columnIndex = 0;
        int keyIndex = 0;
        int fKeyIndex = 0;
        
        //<PHYSICAL MODEL>//
        Element database = generateDatabase(d, dbName, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, DATABASE_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, DATABASE_INDEX, 0), "Oracle11g");
        Element connectionPool = generateConnectionPool(d, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, CONNECTION_POOL_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, CONNECTION_POOL_INDEX, 0), database.getAttribute("id"), database.getAttribute("uid"), cpName, database.getAttribute("pName"), Consts.getRE().getCPUser(), Consts.getRE().getCPPassword(), Consts.getDB().getDBDataSource());
        setRefConnectionPool(d, database, connectionPool);
        
        Element physicalCatalog = generatePhysicalCatalog(d, database, catalogName, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_CATALOG_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_CATALOG_INDEX, 0), "");
        
        Element schema = generateSchema(d, physicalCatalog, schemaName, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, SCHEMA_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, SCHEMA_INDEX, 0));
        
        List<Element> physicalTables = new ArrayList<Element>();
        List<Element> physicalColumns = new ArrayList<Element>();
        List<Element> physicalKeys = new ArrayList<Element>();
        List<Element> physicalForeignKeys = new ArrayList<Element>();
        
        for (int i=0; i<tablesData.size(); i++) {
            Element curTable = generatePhysicalTable(d, schema, (String)tablesData.get(i).get(TableDataGen.TABLEMAP_TABLE_NAME), (String)tablesData.get(i).get(TableDataGen.TABLEMAP_TABLE_DESC), IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_TABLE_INDEX, tableIndex), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_TABLE_INDEX, tableIndex), (String)tablesData.get(i).get(TableDataGen.TABLEMAP_DIM), (String)tablesData.get(i).get(TableDataGen.TABLEMAP_IS_DUMMY), (String)tablesData.get(i).get(TableDataGen.TABLEMAP_GROUP));
            physicalTables.add(curTable);
            
            List<HashMap> columnsData = (List<HashMap>)tablesData.get(i).get(TableDataGen.TABLEMAP_COLUMNS);
            
            for (int j=0; j<columnsData.size(); j++) {
                // set flag for column visibility in structure
                String isPrimaryKey = (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_IS_PRIMARY_KEY);
                String isForeignKey = (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_IS_FOREIGN_KEY);
                
                Element curColumn = generatePhysicalColumn(d, curTable, (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_NAME), IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_COLUMN_INDEX, columnIndex), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_COLUMN_INDEX, columnIndex), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_TYPE), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_FORMAT), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_IS_NULLABLE), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_AGGR_RULE), isPrimaryKey.toLowerCase(), isForeignKey.toLowerCase(), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_DESC));
                physicalColumns.add(curColumn);
                
                if (/*columnsData.get(j).get("IS_PRIMARY_KEY")*/isPrimaryKey.toLowerCase().equals("true")) {
                    Element curKey = generatePhysicalKey(d, curTable, curColumn, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_KEY_INDEX, keyIndex), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_KEY_INDEX, keyIndex), (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_NAME) + "_KEY");
                    physicalKeys.add(curKey);
                    curKey = null;
                    keyIndex++;
                }
                curColumn = null;
                columnIndex++;
                
                isPrimaryKey = null;
                isForeignKey = null;
            }
            columnsData = null;
            curTable = null;
            tableIndex++;
        }
        
        for (int i=0; i<tablesData.size(); i++) {
            List<HashMap> columnsData = (List<HashMap>)tablesData.get(i).get(TableDataGen.TABLEMAP_COLUMNS);
            
            for (int j=0; j<columnsData.size(); j++) {
                String isForeignKey = (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_IS_FOREIGN_KEY);
                
                if (/*columnsData.get(j).get("IS_FOREIGN_KEY")*/isForeignKey.equals("true")) {
                    for (Element curTable : physicalTables) {
                        if (curTable.getAttribute("name").equals(tablesData.get(i).get(TableDataGen.TABLEMAP_TABLE_NAME))) {
                            for (Element curColumn : physicalColumns) {
                                if (curColumn.getAttribute("name").equals(columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_NAME)) && curColumn.getAttribute("parentId").equals(curTable.getAttribute("id"))) {
                                    for (Element curKey : physicalKeys) {
                                        for (Element t : physicalTables) {
                                            if (t.getAttribute("name").equals(columnsData.get(j).get(TableDataGen.COLUMN_MAP_REF_TABLE_NAME))) {
                                                if (curKey.getAttribute("parentId").equals(t.getAttribute("id"))) {
                                                    Element curFKey = generatePhysicalForeignKey(d, curTable, curColumn, curKey, (String)columnsData.get(j).get(TableDataGen.COLUMN_MAP_COLUMN_NAME) + "_FOREIGNKEY", IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_FOREIGN_KEY_INDEX, fKeyIndex), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, PHYSICAL_FOREIGN_KEY_INDEX, fKeyIndex));
                                                    physicalForeignKeys.add(curFKey);
                                                    curFKey = null;
                                                    fKeyIndex++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            columnsData = null;
        }
        //</PHYSICAL MODEL>//
        
        //<BUSINESS MODEL>//
        
        /* 
         * here we use beautify() for table and columns names 
         * using TableDataGen.beautify(String name)
         */
        //arrays
        List<Element> logicalTables = new ArrayList<Element>();
        List<Element> logicalTableSources = new ArrayList<Element>();
        List<Element> logicalColumns = new ArrayList<Element>();
        List<Element> logicalKeys = new ArrayList<Element>();
        List<Element> logicalComplexJoins = new ArrayList<Element>();
        List<Element> columnMappings = new ArrayList<Element>();
        List<Element> measureDfns = new ArrayList<Element>();
        List<Element> aggrRules = new ArrayList<Element>();
        
        //@isadikov: create tech array for containing table dims which should be used with complex table source
        // 1. we switch off complex table sources
        String[] arrayForComplexTSource = /*{Consts.getRE().TABLE_TYPE_DUMMY_FACT, Consts.getRE().TABLE_TYPE_FACT}*/ { "none" };
        
        //@isadikov: create logicalSortColumn, which represents sort column in table 
        // (actually, now it's always table primary key).
        // if table has any other column we make them sort using logicalSortColumn
        // and we also create list of elements
        Element logicalSortColumn = d.createElement("Empty");
        List<Element> logicalColumnsForTable = new ArrayList<Element>();
        
        //indexes
        int lTableIndex = 0;
        int lTableSrcIndex = 0;
        int lColumnIndex = 0;
        int lKeyIndex = 0;
        int lComplexJoinIndex = 0;
        int measureDfnIndex = 0;
        int aggrRuleIndex = 0;
        
        String bmName = "LM" + nextModelNumber + " " + pmName;
        Element businessModel = generateBusinessModel(d, bmName, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, BUSINESS_MODEL_INDEX, 0), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, BUSINESS_MODEL_INDEX, 0));
        
        for (Element physicalTable : physicalTables) {
            String curTableName = physicalTable.getAttribute("name");
            String curTableSrcName = physicalTable.getAttribute("name") + "_SRC";
            String curTableDesc = physicalTable.getAttribute("dispName");
            String curTableGroup = physicalTable.getAttribute("group");
            Element logicalTable = generateLogicalTable(d, businessModel, TableDataGen.beautify(curTableName), curTableDesc, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, LOGICAL_TABLE_INDEX, lTableIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, LOGICAL_TABLE_INDEX, lTableIndex), physicalTable.getAttribute("dim"), physicalTable.getAttribute("isDummy"), curTableGroup);
            Element logicalTableSource = generateLogicalTableSource(d, logicalTable, physicalTable, physicalTables, physicalForeignKeys, physicalKeys, curTableSrcName, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, LOGICAL_TABLE_SOURCE_INDEX, lTableSrcIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, LOGICAL_TABLE_SOURCE_INDEX, lTableSrcIndex), arrayForComplexTSource);
            
            setRefLogicalTableSource(d, logicalTable, logicalTableSource);
            //@isadikov: declare new logicalSortColumn for pivot table if exists
            if (logicalTable.getAttribute("dim").equals(Consts.getRE().TABLE_TYPE_PIVOT_DIM)) {
                logicalSortColumn = d.createElement("Empty");
            }
            
            for (Element physicalColumn : physicalColumns) {
                if (physicalColumn.getAttribute("parentUid").equals(physicalTable.getAttribute("uid"))) {
                    String isPrimaryKey = "false";
                    String isForeignKey = "false";
                    
                    if (physicalColumn.getAttribute("isPrimaryKey").equals("true")) {
                        isPrimaryKey = "true";
                    }
                    
                    if (physicalColumn.getAttribute("isForeignKey").equals("true")) {
                        isForeignKey = "true";
                    }
                    
                    Element logicalColumn = generateLogicalColumn(d, logicalTable, TableDataGen.beautify(physicalColumn.getAttribute("name")), IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, LOGICAL_COLUMN_INDEX, lColumnIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, LOGICAL_COLUMN_INDEX, lColumnIndex), physicalColumn.getAttribute("aggrType"), isPrimaryKey, isForeignKey, physicalColumn.getAttribute("dispName"));
                    
                    if (!showFullStructure && logicalColumn.getAttribute("isForeignKey").equals("true")) {
                        continue;
                    } else {
                        Element columnMapping = generateColumnMapping(d, logicalTableSource, logicalColumn, physicalColumn);
                        columnMappings.add(columnMapping);
                        
                        if (!logicalColumn.getAttribute("aggrType").equals("")) {
                            Element measure = generateMeasureDfn(d, logicalColumn, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, MEASURE_DFN_INDEX, measureDfnIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, MEASURE_DFN_INDEX, measureDfnIndex), "Measure");
                            Element aggrRule = generateAggrRule(d, logicalColumn, measure, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, AGG_RULE_INDEX, aggrRuleIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, AGG_RULE_INDEX, aggrRuleIndex));
                            
                            setRefAggrRule(d, measure, aggrRule);
                            
                            measureDfns.add(measure);
                            measure = null;
                            measureDfnIndex++;
                            
                            aggrRules.add(aggrRule);
                            aggrRule = null;
                            aggrRuleIndex++;
                        }
                        
                        setRefLogicalColumn(d, logicalTable, logicalColumn);
                        
                        for (Element physicalKey : physicalKeys) {
                            Node physicalKeyColumnNode = physicalKey.getElementsByTagName("Columns").item(0).getChildNodes().item(0);
                            Element physicalKeyColumn = (Element)physicalKeyColumnNode;
                            
                            if (physicalKey.getAttribute("parentUid").equals(physicalTable.getAttribute("uid")) && physicalKeyColumn.getAttribute("id").equals(physicalColumn.getAttribute("id"))) {
                                String logicalKeyName = "L" + IDGen.generatePrfx(101, 999) + "_" + physicalKey.getAttribute("name");
                                Element logicalKey = generateLogicalKey(d, logicalTable, logicalColumn, logicalKeyName, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, LOGICAL_KEY_INDEX, lKeyIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, LOGICAL_KEY_INDEX, lKeyIndex));
                                
                                logicalKeys.add(logicalKey);
                                logicalKey = null;
                                logicalKeyName = null;
                                lKeyIndex++;
                            }
                        }
                        
                        // @isadikov: add sortable property
                        if (logicalTable.getAttribute("dim").equals(Consts.getRE().TABLE_TYPE_PIVOT_DIM)) {
                            logicalColumnsForTable.add(logicalColumn);
                            
                            if (logicalColumn.getAttribute("isPrimaryKey").equals("true") && logicalColumn.getAttribute("isForeignKey").equals("false")) {
                                logicalSortColumn = logicalColumn;
                            }
                        }
                        
                        logicalColumns.add(logicalColumn);
                        lColumnIndex++;
                    }
                }
            }
            
            //@isadikov: check id column and set sort order for other columns in table
            if (logicalTable.getAttribute("dim").equals(Consts.getRE().TABLE_TYPE_PIVOT_DIM)) {
                for (Element lptable : logicalColumnsForTable) {
                    if (!logicalSortColumn.getTagName().equals("Empty")) {
                        setLogicalColumnCustomSortOrder(d, lptable, logicalSortColumn);
                    }
                }
            }
            
            logicalTables.add(logicalTable);
            logicalTableSources.add(logicalTableSource);
            
            logicalTable = null;
            logicalTableSource = null;
            curTableSrcName = null;
            curTableName = null;
            //@isadikov: description ref for logical table
            //will be used as a display name for presentation table
            curTableDesc = null;
            //@isadikov: group for logical table, used to divide tables to groups
            curTableGroup = null;
            //@isadikov: sort column is null after table is loaded in list, because we need a new sort column for the next pivot table
            logicalSortColumn = null;
            logicalColumnsForTable.clear();
            
            lTableIndex++;
        }
        
        //logical complex joins
        for (Element physicalFKey : physicalForeignKeys){
            Node RefPhysicalColumnNode = physicalFKey.getElementsByTagName("Columns").item(0).getChildNodes().item(0);
            Element RefPhysicalColumn = (Element)RefPhysicalColumnNode;
            
            Element logicalFactTable = d.createElement("LogicalTable");
            
            for (Element pFactTable : physicalTables) {
                for (Element pColumn : physicalColumns) {
                    if (RefPhysicalColumn.getAttribute("id").equals(pColumn.getAttribute("id")) && pColumn.getAttribute("parentId").equals(pFactTable.getAttribute("id"))) {
                        for (Element curTableSource : logicalTableSources) {
                            Element tempLink = (Element)curTableSource.getElementsByTagName("Link").item(0);
                            Element tempStartNode = (Element)tempLink.getElementsByTagName("StartNode").item(0);
                            // @depricated
                            // because of unknown node defining
                            //Node curPTableLinkNode = curTableSource.getElementsByTagName("Link").item(0).getChildNodes().item(0).getChildNodes().item(0);
                            Node curPTableLinkNode = tempStartNode.getChildNodes().item(0);
                            
                            Element curPTableLink = (Element)curPTableLinkNode;
                            if (curPTableLink.getAttribute("id").equals(pFactTable.getAttribute("id"))) {
                                for (Element curLTable : logicalTables) {
                                    if (curTableSource.getAttribute("parentId").equals(curLTable.getAttribute("id"))) {
                                        logicalFactTable = curLTable;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            Node RefPhysicalKeyNode = physicalFKey.getElementsByTagName("CounterPartKey").item(0).getChildNodes().item(0);
            Element RefPhysicalKey = (Element)RefPhysicalKeyNode;
            
            Element logicalDimTable = d.createElement("LogicalTable");
            
            for (Element pFactTable : physicalTables) {
                for (Element pKey : physicalKeys) {
                    if (RefPhysicalKey.getAttribute("id").equals(pKey.getAttribute("id")) && pKey.getAttribute("parentId").equals(pFactTable.getAttribute("id"))) {
                        for (Element curTableSource : logicalTableSources) {
                            Element tempLink = (Element)curTableSource.getElementsByTagName("Link").item(0);
                            Element tempStartNode = (Element)tempLink.getElementsByTagName("StartNode").item(0);
                            // @depricated
                            // because of unknown node defining
                            //Node curPTableLinkNode = curTableSource.getElementsByTagName("Link").item(0).getChildNodes().item(0).getChildNodes().item(0);
                            Node curPTableLinkNode = tempStartNode.getChildNodes().item(0);
                            Element curPTableLink = (Element)curPTableLinkNode;
                            if (curPTableLink.getAttribute("id").equals(pFactTable.getAttribute("id"))) {
                                for (Element curLTable : logicalTables) {
                                    if (curTableSource.getAttribute("parentId").equals(curLTable.getAttribute("id"))) {
                                        logicalDimTable = curLTable;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // here we have two tables to set up the complex logical join
            // 1. logicalFactTable
            // 2. logicalDimTable
            // they were settled in an array for the first release
            // --> Element[] joiningTables = new Element[2];
            // --> joiningTables[0] = logicalFactTable;
            // --> joiningTables[1] = logicalDimTable;
            // but when modified they are just used as Element instances in generateLogicalComplexJoin() method
            // like --> generateLogicalComplexJoin(
            //                     d, 
            //                     id, 
            //                     uid
            //                     logicalFactTable, 
            //                     logicalDimTable   
            //                     joinType);
            
            Element logicalComplexJoin = generateLogicalComplexJoin(d, IDGen.generateIdTemplate(BUSINESS_MODEL_INDEX, LOGICAL_COMPLEX_JOIN_INDEX, lComplexJoinIndex), IDGen.generateUidTemplate(BUSINESS_MODEL_INDEX, LOGICAL_COMPLEX_JOIN_INDEX, lComplexJoinIndex), logicalFactTable, logicalDimTable, "Inner");
            logicalComplexJoins.add(logicalComplexJoin);
            logicalComplexJoin = null;
            lComplexJoinIndex++;
        }     
        //</BUSINESS MODEL>//
        
        //<PRESENTATION CATALOG>//
        List<Element> presentationTables = new ArrayList<Element>();
        List<Element> presentationColumns = new ArrayList<Element>();
        //@isadikov: create list of presentation group tables
        // if there is one more group we create group folders for the rest of the tables
        List<Element> presentationGroupTables = new ArrayList<Element>();
        boolean hasOneGroup = true;
        
        int PMTableIndex = 0;
        int PMColumnIndex = 0;
        
        String prCatalogName = "PM" + nextModelNumber + " " + pmName;
        
        Element presentationCatalog = generatePresentationCatalog(d, businessModel, prCatalogName, pmName, pmDesc, IDGen.generateIdTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_MODEL_INDEX, 0), IDGen.generateUidTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_MODEL_INDEX, 0));
        
        //@isadikov: check and fill presentationGroupTables
        if (showGroupFolders) {
            if (tablesGroupsData.size() <= 1 ) {
                hasOneGroup = true;
            } else {
                hasOneGroup = false;
                
                for (int index=0; index<tablesGroupsData.size(); index++) {
                    String currentGroup = tablesGroupsData.get(index);
                    String currentGroupName = "Group" + currentGroup;
                    String currentGroupDispName = Consts.getRE().SUBJECT_AREA_LOCAL_GROUP_NAME + " " + currentGroup;
                    
                    Element presentationGroupTable = generatePresentationGroupTable(d, presentationCatalog, currentGroupName, IDGen.generateIdTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_TABLE_INDEX, index*99), IDGen.generateUidTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_TABLE_INDEX, index*99), currentGroupDispName, currentGroup);
                    
                    presentationGroupTables.add(presentationGroupTable);
                    presentationGroupTable = null;
                }
            }
        }
        
        for (Element lTable : logicalTables) {
            //@isadikov: if logical table has "isDummy" tag we do not set presentation table and columns to the presentation catalog
            if (lTable.getAttribute("isDummy").equals("false")) {
                Element presentationTable = generatePresentationTable(d, presentationCatalog, lTable.getAttribute("name"), IDGen.generateIdTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_TABLE_INDEX, PMTableIndex), IDGen.generateUidTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_TABLE_INDEX, PMTableIndex), lTable.getAttribute("dispName"), lTable.getAttribute("group"));
                
                setRefPresentationTable(d, presentationCatalog, presentationTable);
                
                for (Element lColumn : logicalColumns) {
                    if (lColumn.getAttribute("parentId").equals(lTable.getAttribute("id"))) {
                        Element presentationColumn = generatePresentationColumn(d, presentationTable, lColumn, lColumn.getAttribute("name"), IDGen.generateIdTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_COLUMN_INDEX, PMColumnIndex), IDGen.generateUidTemplate(PRESENTATION_MODEL_INDEX, PRESENTATION_COLUMN_INDEX, PMColumnIndex), lColumn.getAttribute("dispName"));
                        
                        /* set visibility for presentation columns */
                        // we delete id objects and created date columns from presentation level,
                        // because there is no need to display them
                        /***/
                        if (!showFullStructure) {
                            if (lColumn.getAttribute("isForeignKey").equals("false") && lColumn.getAttribute("isPrimaryKey").equals("false") && !isLogicalCreatedDate(lColumn.getAttribute("name"))) {
                                setRefPresentationColumn(d, presentationTable, presentationColumn);
                                presentationColumns.add(presentationColumn);
                            }
                        } else {
                            setRefPresentationColumn(d, presentationTable, presentationColumn);
                            presentationColumns.add(presentationColumn);
                        }
                        /***/
                        
                        presentationColumn = null;
                        PMColumnIndex++;
                    }
                }
                
                //@isadikov: check for presentation table whether it contains any column or not
                // if it does not contain "Columns" we remove "Columns" and "RemovedPresentationColumns" for XUDML being valid
                // -- check switched off
                /*
                if (!presentationTable.getElementsByTagName("Columns").item(0).hasChildNodes()) {
                    presentationTable.removeChild(presentationTable.getElementsByTagName("Columns").item(0));
                    presentationTable.removeChild(presentationTable.getElementsByTagName("RemovedPresentationColumns").item(0));
                }
                */
                
                //@isadikov: check group for presentation table.
                // if it exists we add presentation table to the source group table
                if (showGroupFolders && !hasOneGroup) {
                    for (Element pgTable : presentationGroupTables) {
                        if (checkPresentationTableGroup(pgTable, presentationTable)) {
                            setRefPresentationGroupTable(d, pgTable, presentationTable);
                        }
                    }
                }
                
                presentationTables.add(presentationTable);
                presentationTable = null;
                PMTableIndex++;
            }
        }
        
        //@isadikov: add all tables in presentationGroupTables into presentationTables
        if (showGroupFolders && !hasOneGroup) {
            presentationTables.addAll(presentationGroupTables);
        }
        presentationGroupTables = null;
        hasOneGroup = true;
        
         //</PRESENTATION CATALOG>//
        
        
        database.removeAttribute("pName");
        declare.appendChild(database);
        
        for (Element t : physicalTables) {
            t.removeAttribute("pName");
            t.removeAttribute("dim");
            t.removeAttribute("isDummy");
            t.removeAttribute("dispName");
            t.removeAttribute("group");
            
            declare.appendChild(t);
        }
        
        for (Element c : physicalColumns) {
            c.removeAttribute("pName");
            c.removeAttribute("aggrType");
            c.removeAttribute("isPrimaryKey");
            c.removeAttribute("isForeignKey");
            c.removeAttribute("dispName");
            
            declare.appendChild(c);
        }
        
        for (Element key : physicalKeys) {
            key.removeAttribute("pName");
            declare.appendChild(key);
        }
        
        connectionPool.removeAttribute("pName");
        declare.appendChild(connectionPool);
        
        physicalCatalog.removeAttribute("pName");
        declare.appendChild(physicalCatalog);
        
        schema.removeAttribute("pName");
        declare.appendChild(schema);
        
        for (Element fKey : physicalForeignKeys) {
            fKey.removeAttribute("pName");
            declare.appendChild(fKey);
        }
        
        //BusinessModel
        businessModel.removeAttribute("pName");
        declare.appendChild(businessModel);
        
        //LogicalTable
        for (Element lTable : logicalTables) {
            lTable.removeAttribute("pName");
            lTable.removeAttribute("dim");
            lTable.removeAttribute("isDummy");
            lTable.removeAttribute("dispName");
            lTable.removeAttribute("group");
            
            declare.appendChild(lTable);
        }
        
        //LogicalColumn
        for (Element lColumn : logicalColumns) {
            lColumn.removeAttribute("pName");
            lColumn.removeAttribute("aggrType");
            lColumn.removeAttribute("isPrimaryKey");
            lColumn.removeAttribute("isForeignKey");
            lColumn.removeAttribute("dispName");
            
            declare.appendChild(lColumn);
        }
        
        //LogicalTableSource
        for (Element lTableSrc : logicalTableSources) {
            lTableSrc.removeAttribute("pName");
            declare.appendChild(lTableSrc);
        }
        
        //LogicalKey
        for (Element lKey : logicalKeys) {
            lKey.removeAttribute("pName");
            declare.appendChild(lKey);
        }
        
        //MeasureDfn
        for (Element measure : measureDfns) {
            measure.removeAttribute("pName");
            declare.appendChild(measure);
        }
        
        //AggrRule
        for (Element agg : aggrRules) {
            agg.removeAttribute("pName");
            declare.appendChild(agg);
        }
        
        //LogicalComplexJoin
        for (Element lComplexJoin : logicalComplexJoins) {
            lComplexJoin.removeAttribute("pName");
            declare.appendChild(lComplexJoin);
        }
        
        //PresentationCatalog
        presentationCatalog.removeAttribute("pName");
        declare.appendChild(presentationCatalog);
        
        //PresentationTable
        for (Element pmTable : presentationTables) {
            pmTable.removeAttribute("pName");
            pmTable.removeAttribute("group");
            
            declare.appendChild(pmTable);
            
        }
        
        //PresentationColumn
        for (Element pmColumn : presentationColumns) {
            pmColumn.removeAttribute("pName");
            declare.appendChild(pmColumn);
        }
        
        //Column Mapping
        for (Element cMap : columnMappings) {
            declare.appendChild(cMap);
        }
        
        return d;
    }
    
    //DELETE objects
    public static Element generateRefDatabase(Document d, String pmName, String nextModelNumber) {
        Element RefDatabase = d.createElement("RefDatabase");
        String dbName = "DB" + nextModelNumber + "_" + pmName;
        
        RefDatabase.setAttribute("qualifiedName", "&quot;" + dbName + "&quot;");
        
        dbName = null;
        
        return RefDatabase;
    }
    
    public static Element generateRefBusinessModel(Document d, String pmName, String nextModelNumber) {
        Element RefBusinessModel = d.createElement("RefBusinessModel");
        String bmName = "LM" + nextModelNumber + " " + pmName;
        
        RefBusinessModel.setAttribute("qualifiedName", "&quot;" + bmName + "&quot;");
        
        bmName = null;
        
        return RefBusinessModel;
    }
    
    public static Element generateRefPresentationCatalog(Document d, String pmName, String nextModelNumber) {
        Element RefPresentationCatalog = d.createElement("RefPresentationCatalog");
        
        String prCatalogName = "PM" + nextModelNumber + " " + pmName;
        
        RefPresentationCatalog.setAttribute("qualifiedName", "&quot;" + prCatalogName + "&quot;");
        
        return RefPresentationCatalog;
    }
    
    public static Element generateRefVariable(Document d, String name) {
        
        Element RefVariable = d.createElement("RefVariable");
        RefVariable.setAttribute("qualifiedName", "&quot;" + name + "&quot;");
        
        return RefVariable;
    }
    
    public static Document getRepositoryDeletesXML(Document d, String xmlstring, String pmName, String nextModelNumber) {
        if (d == null) {
            d = Common.generateDom(xmlstring);    
        }
        
        Element DeleteObjects = d.createElement("DeleteObjects");
        
        Element refDatabase = generateRefDatabase(d, pmName, nextModelNumber);
        Element refBusinessModel = generateRefBusinessModel(d, pmName, nextModelNumber);
        Element refPresentationCatalog = generateRefPresentationCatalog(d, pmName, nextModelNumber);
        
        DeleteObjects.appendChild(refDatabase);
        DeleteObjects.appendChild(refBusinessModel);
        DeleteObjects.appendChild(refPresentationCatalog);

        refPresentationCatalog = null;
        refBusinessModel = null;        
        refDatabase = null;
        
        Element root = (Element)d.getDocumentElement().getElementsByTagName("DELETE").item(0);
        root.appendChild(DeleteObjects);
        
        return d;
    }
    
    private static void WSGetRepositoryChangesXML(Document d, String pmName, String nextModelNumber, String pmDesc, List<HashMap> tablesData, List<String> tablesGroupsData, boolean showFullStructure, boolean showGroupFolders) {
        getRepositoryChangesXML(d, pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, showFullStructure, showGroupFolders);
    }
    
    private static void WSGetRepositoryDeletesXML(Document d, String pmName, String nextModelNumber) {
        getRepositoryDeletesXML(d, "", pmName, nextModelNumber);
    }
    
    public static Document WSGetRepositoryChangesAndDeletesXML(String pmName, String nextModelNumber, String pmDesc, List<HashMap> tablesData, List<String> tablesGroupsData, boolean showFullStructure, boolean showGroupFolders) {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"no\"?>" + 
        "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
        "<DECLARE></DECLARE>" + 
        "<DELETE></DELETE>" + 
        "</Repository>";
        
        Document d = Common.generateDom(xmlstring);
        
        WSGetRepositoryChangesXML(d, pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, showFullStructure, showGroupFolders);
        WSGetRepositoryDeletesXML(d, pmName, nextModelNumber);
        
        return d;
        
    }
    
    /*
     * init methods for starting application
     * when it starts these command will be executed
     */
    //make init changes
    public static Document initRepositoryChanges() {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                           + "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                           + "<DECLARE>"
                           + "</DECLARE>"
                           + "</Repository>";
        
        Document d = Common.generateDom(xmlstring);
        
        Element declare = (Element)d.getDocumentElement().getChildNodes().item(0);
        
        Element userVariable = generateRepositoryVariable(d, Consts.getRE().RPD_USERNAME_VARIABLE, Consts.getDB().DB_SCHEME_NAME, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 0));
        Element passVariable = generateRepositoryVariable(d, Consts.getRE().RPD_PASSWORD_VARIABLE, Consts.getDB().DB_SCHEME_PASSWORD, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 1), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 1));
        
        declare.appendChild(userVariable);
        declare.appendChild(passVariable);
        
        return d;
    }
    
    //make delete changes
    public static Document initRepositoryDeletes() {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                           + "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                           + "<DELETE>"
                           + "</DELETE>"
                           + "</Repository>";
        
        Document d = Common.generateDom(xmlstring);
        
        Element DeleteObjects = d.createElement("DeleteObjects");
        
        Element refUserVariable = generateRefVariable(d, Consts.getRE().RPD_USERNAME_VARIABLE);
        Element refPassVariable = generateRefVariable(d, Consts.getRE().RPD_PASSWORD_VARIABLE);
        
        DeleteObjects.appendChild(refUserVariable);
        DeleteObjects.appendChild(refPassVariable);

        refUserVariable = null;
        refPassVariable = null;
        
        Element root = (Element)d.getDocumentElement().getElementsByTagName("DELETE").item(0);
        root.appendChild(DeleteObjects);
        
        return d;
    }
    
    private static void WSInitRepositoryChanges(Document d) {
        Element declare = (Element)d.getDocumentElement().getElementsByTagName("DECLARE").item(0);
        
        Element userVariable = generateRepositoryVariable(d, Consts.getRE().RPD_USERNAME_VARIABLE, Consts.getDB().DB_SCHEME_NAME, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 0), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 0));
        Element passVariable = generateRepositoryVariable(d, Consts.getRE().RPD_PASSWORD_VARIABLE, Consts.getDB().DB_SCHEME_PASSWORD, IDGen.generateIdTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 1), IDGen.generateUidTemplate(PHYSICAL_MODEL_INDEX, VARIABLE_INDEX, 1));
        
        declare.appendChild(userVariable);
        declare.appendChild(passVariable);
    }
    
    private static void WSInitRepositoryDeletes(Document d) {
        Element DeleteObjects = d.createElement("DeleteObjects");
        
        Element refUserVariable = generateRefVariable(d, Consts.getRE().RPD_USERNAME_VARIABLE);
        Element refPassVariable = generateRefVariable(d, Consts.getRE().RPD_PASSWORD_VARIABLE);
        
        DeleteObjects.appendChild(refUserVariable);
        DeleteObjects.appendChild(refPassVariable);

        refUserVariable = null;
        refPassVariable = null;
        
        Element root = (Element)d.getDocumentElement().getElementsByTagName("DELETE").item(0);
        root.appendChild(DeleteObjects);
    }
    
    public static Document WSInitRepositoryChangesAndDeletes() {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                           "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<DECLARE></DECLARE>" +
                            "<DELETE></DELETE>" +
                           "</Repository>";
        
        Document d = Common.generateDom(xmlstring);
        
        WSInitRepositoryDeletes(d);
        WSInitRepositoryChanges(d);
        
        return d;
    }
    
    // @isadikov: test for generate XML
    /*
    public static void main (String[] args) {
        String xmlstring = "<?xml version=\"1.0\"?>\n" + 
        "<cube name=\"PM13_SMALL_TEST\" desc=\"test model\" isNewModel=\"false\">\n" + 
        "  <tables>\n" + 
        "    <table name=\"PM004_F001\" desc=\"Показатели группы 1\" type=\"fact\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D003_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D003\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D003_CITY\"/>\n" + 
        "        <col name=\"D004_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D004\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D004_TYPE1\"/>\n" + 
        "        <col name=\"D005_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D005\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D005_TYPE2\"/>\n" + 
        "        <col name=\"D006_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D006\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D006_TYPE3\"/>\n" + 
        "        <col name=\"D007_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D007\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D007_AREA1\"/>\n" + 
        "        <col name=\"D008_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D008\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D008_CITY1\"/>\n" + 
        "        <col name=\"D009_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D009\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D009_TYPE4\"/>\n" + 
        "        <col name=\"D010_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D010\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D010_TYPE5\"/>\n" + 
        "        <col name=\"O011_M01_SUM\" type=\"NUMBER(18,2)\" desc=\"Сумма\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"sum\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D001_REGION\" desc=\"Справочник =Регион=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O001_A01_REGION\" type=\"VARCHAR2(512 CHAR)\" desc=\"Регион\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D002_AREA\" desc=\"Справочник =Область=\" type=\"parent_dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D001_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D001\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D001_REGION\"/>\n" + 
        "        <col name=\"O002_A01_AREA\" type=\"VARCHAR2(512 CHAR)\" desc=\"Область\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D003_CITY\" desc=\"Справочник =Город=\" type=\"parent_dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D002_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D002\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D002_AREA\"/>\n" + 
        "        <col name=\"O003_A01_CITY\" type=\"VARCHAR2(512 CHAR)\" desc=\"Город\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D004_TYPE1\" desc=\"Справочник =Тип 1=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O004_A01_TYPE1\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип 1\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D005_TYPE2\" desc=\"Справочник =Тип 2=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O005_A01_TYPE2\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип 2\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D006_TYPE3\" desc=\"Справочник =Тип 3=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O006_A01_TYPE3\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип 3\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D007_AREA1\" desc=\"Справочник =Область 1=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O007_A01_AREA1\" type=\"VARCHAR2(512 CHAR)\" desc=\"Область 1\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D008_CITY1\" desc=\"Справочник =Город 1=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O008_A01_CITY1\" type=\"VARCHAR2(512 CHAR)\" desc=\"Город 1\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D009_TYPE4\" desc=\"Справочник =Тип 4=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O009_A01_TYPE4\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип 4\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_D010_TYPE5\" desc=\"Справочник =Тип 5=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O010_A01_TYPE5\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип 5\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM004_F002\" desc=\"Показатели группы 2\" type=\"fact\" group=\"2\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D002_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D002\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM004_D002_AREA\"/>\n" + 
        "        <col name=\"O013_M01_COUNT\" type=\"NUMBER(18,2)\" desc=\"Количество\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"sum\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "  </tables>\n" + 
        "</cube>";
        
        List<HashMap> tablesData = (List<HashMap>)TableDataGen.loadTableData(xmlstring).get("TABLES");
        List<String> tablesGroupsData = (List<String>)TableDataGen.loadTableData(xmlstring).get("GROUPS");
        //System.out.println(tablesData);
        Document d = WSGetRepositoryChangesAndDeletesXML("Test3", "11.1", "Тест модель", tablesData, tablesGroupsData, true, true);
        
        System.out.println(Common.XMLReplaceSpecCharacters(Common.DomToXMLString(d)));
        //System.out.println(d);
        
        //System.out.println(Common.DomToXMLString(WSInitRepositoryChangesAndDeletes()));
    }
    */
}
