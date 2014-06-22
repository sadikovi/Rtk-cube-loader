package sitronics.data;

import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.*;
import jxl.Cell;

import jxl.read.biff.BiffException;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import sitronics.data.utils.Common;
import sitronics.data.utils.consts.Consts;

/*
 * Public class Excel
 * Created for working with excel workbooks which have an XDO_CONFIGURATION sheet
 * If you want to make a new release please make sure that methods are rewritten
 * 
 */
public class Excel {
    
    public HashMap ExcelData = new HashMap();
    
    // configuration sheet variable
    private static Sheet configSheet;
    // global columns info (name, desc, startRow, position etc.) variable
    private static List<HashMap> GlobalColumnsList = new ArrayList<HashMap>();
    // all load sheets info (name, rowsCount, Sheet objects) variable
    private static List<HashMap> sheetsInfo = new ArrayList<HashMap>();
    // all workbook groups info (group name, isPivot flag, maxRows and startRow) variable
    private static List<HashMap> workbookGroups = new ArrayList<HashMap>();
    
    // default parent position is "0"
    private static String defaultParentPosition = "0";
    // flag for using order in GlobalColumnsInfo (if you don't need it just switch it off)
    boolean isOrderUsed = false;
    
    private static final String SHEETS_INFO_MAP_ROWS = "ROWS";
    private static final String SHEETS_INFO_MAP_SHEET = "SHEET";
    private static final String SHEETS_INFO_MAP_SHEET_NAME = "SHEET_NAME";
    private static final String WORKBOOK_GROUPS_MAP_GROUP_INDEX = "GROUP_INDEX";
    private static final String WORKBOOK_GROUPS_MAP_IS_PIVOT = "IS_PIVOT";
    private static final String WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX = "MAX_ROWS";
    private static final String WORKBOOK_GROUPS_MAP_START_ROW_INDEX = "START_ROW";
    private static final String PIVOT_ELEMENT_MAP_KEY = "KEY";
    private static final String PIVOT_ELEMENT_MAP_VALUE = "VALUE";
    
    
    public Excel() {
        super();
    }

    /**
     * Returns Excel Instance as Workbook object.
     * 
     * @param file
     * @return Workbook
     * @throws IOException
     * @throws BiffException
     */
    public Workbook getExcelInstance(InputStream file) throws IOException, BiffException {
        return Workbook.getWorkbook(file);
    }

    /**
     * Returns Workbook instance using workbook settings.
     * 
     * @param file
     * @param ws
     * @return
     * @throws IOException
     * @throws BiffException
     */
    public Workbook getExcelInstance(InputStream file, WorkbookSettings ws) throws IOException, BiffException {
        return Workbook.getWorkbook(file, ws);
    }

    /**
     * Returns Workbook instance using encodeString which is a part of workbook settings.
     * 
     * @param file
     * @param encodeString
     * @return
     * @throws IOException
     * @throws BiffException
     */
    public Workbook getExcelInstance(InputStream file, String encodeString) throws IOException, BiffException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setEncoding(encodeString);
        return Workbook.getWorkbook(file, ws);
    }

    /**
     * Returns success message if HashMap list is completed successfully.
     * Otherwise error description.
     * 
     * @param list
     * @return
     */
    public String isCompletedSuccessfully(List<HashMap> list) {
        
        if (list.size() <= 0) {
            return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMNS_LIST_EMPTY;
        }
        
        for (HashMap item : list) {
            if (!item.containsKey(Consts.getEX().DIMENSION)
                || !item.containsKey(Consts.getEX().COLUMN_SHEET)
                || !item.containsKey(Consts.getEX().COLUMN_DATA_GROUP)
                || !item.containsKey(Consts.getEX().COLUMN_POSITION_IN_GROUP)
                || !item.containsKey(Consts.getEX().COLUMN_PARENT_POSITION)
                || !item.containsKey(Consts.getEX().COLUMN_START_ROW)
                || !item.containsKey(Consts.getEX().COLUMN_NAME)
                || !item.containsKey(Consts.getEX().COLUMN_DESCRIPTION)
                || !item.containsKey(Consts.getEX().COLUMN_TYPE)
                || !item.containsKey(Consts.getEX().COLUMN_FORMAT)
                || !item.containsKey(Consts.getEX().COLUMN_DB_TYPE)
                || !item.containsKey(Consts.getEX().COLUMN_AGGR_RULE)
            ) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_ATTRIBUTE_MISSED;
            }
            
            if (item.get(Consts.getEX().COLUMN_TYPE).equals(Consts.getEX().UNKNOWN_STRING)) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_TYPE_UNDEFINED;
            }
            
            if (item.get(Consts.getEX().COLUMN_SHEET).equals(Consts.getEX().UNKNOWN_STRING)) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_SHEET_UNKNOWN;
            }
            
            if ((Integer)item.get(Consts.getEX().COLUMN_DATA_GROUP) < 0) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_GROUP_INCORRECT;
            }
            
            if ((Integer)item.get(Consts.getEX().COLUMN_POSITION_IN_GROUP) < 0) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_POSITION_INCORRECT;
            }
            
            if (item.get(Consts.getEX().COLUMN_PARENT_POSITION).equals(Consts.getEX().UNKNOWN_STRING)) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_PARENT_POS_INCORRECT;
            }
            
            if ((Integer)item.get(Consts.getEX().COLUMN_START_ROW) < 0) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_START_ROW_INCORRECT;
            }
            
            if (!item.get(Consts.getEX().COLUMN_AGGR_RULE).equals(Consts.getEX().UNKNOWN_STRING) 
                && !item.get(Consts.getEX().COLUMN_TYPE).equals(Consts.getEX().NUMBER_TYPE)) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CS_COLUMN_AGG_RULE_INCORRECT;
            }
            
            item = null;
        }
        
        return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
    }

    /**
     * Returns true if sheet exists in current workbook.
     * 
     * @param workbook
     * @param sheet
     * @return
     */
    public static boolean isSheetInWorkbook(Workbook workbook, String sheet) {
        for (String t : workbook.getSheetNames()) {
            if (sheet.equals(t)) {
                return true;
            } else {
                continue;
            }
        }
                
        return false;
    }
    
    /**
     * Returns true if excel file (Workbook object has a configuration list).
     * See "Consts.getEX().CONFIGURATION_SHEET" constant for more information.
     * 
     * @param configSheet
     * @return boolean
     */
    private static boolean hasConfigurationSheet(Sheet configSheet) {
        if (configSheet == null || configSheet.getRows() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if workbook has all the sheets which are declared on the configuration list.
     * 
     * @param workbook
     * @return
     */
    public static boolean hasAllSheets(Workbook workbook, Sheet configSheet) {
        //we start with 2nd row, because the first row is a declaration headline
        for (int i=Consts.getEX().CONFIGURATION_LIST_START_ROW; i<=configSheet.getRows(); i++) {
            String c = configSheet.getCell(Consts.getEX().CFG_SHEET_NAME + i).getContents().trim();
            
            if (!isSheetInWorkbook(workbook, c) && c.length() > 0) {
                return false;
            } else {
                continue;
            }
        }
        
        return true;
    }
    
    /**
     * Returns HashMap which contains information about workbook sheets 
     * and their main parameters (like rows number and etc.).
     * 
     * @param workbook
     * @param configSheet
     * @return
     */
    public List<HashMap> getSheetsInfoInWorkbook(Workbook workbook, Sheet configSheet) {
        List<HashMap> res = new ArrayList<HashMap>();
        
        Sheet[] fSheets = workbook.getSheets();
        List<String> workbookGroups = new ArrayList<String>();
        
        for (int i=Consts.getEX().CONFIGURATION_LIST_START_ROW; i<=configSheet.getRows(); i++) {
            String c = configSheet.getCell(Consts.getEX().CFG_SHEET_NAME + i).getContents().trim();
            if (!workbookGroups.contains(c)) {
                workbookGroups.add(c);    
            }
        }
        
        for (Sheet t : fSheets) {
            if (!t.equals(configSheet)) {
                if (workbookGroups.contains(t.getName())) {
                    HashMap resItem = new HashMap();
                    resItem.put(SHEETS_INFO_MAP_SHEET, t);
                    resItem.put(SHEETS_INFO_MAP_SHEET_NAME, t.getName());
                    resItem.put(SHEETS_INFO_MAP_ROWS, t.getRows());
                    
                    res.add(resItem);
                    resItem = null;
                }
            }
        }
        
        return res;
    }
    
    /**
     * Method for fulfilling workbookGroups list 
     * using group number and pivot row position.
     * 
     * @param workbookGroups
     * @param groupNum
     * @param pivotRowPos
     */
    public static void getWorkbookGroups(List<HashMap> workbookGroups, int groupNum, int pivotRowPos, int cMaxRows, int cMinStartRow) {
        HashMap groupMap = new HashMap();
        groupMap.put(WORKBOOK_GROUPS_MAP_GROUP_INDEX, groupNum);
        groupMap.put(WORKBOOK_GROUPS_MAP_IS_PIVOT, (pivotRowPos==Consts.getEX().UNKNOWN_NUMBER)?"false" : "true");
        groupMap.put(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX, cMaxRows);
        groupMap.put(WORKBOOK_GROUPS_MAP_START_ROW_INDEX, cMinStartRow);
        
        int theSameGroup = 0;
        
        if (workbookGroups.size() <= 0) {
            workbookGroups.add(groupMap);
        } else {
            for (HashMap item : workbookGroups) {
                if (item.get(WORKBOOK_GROUPS_MAP_GROUP_INDEX).equals(groupMap.get(WORKBOOK_GROUPS_MAP_GROUP_INDEX))) {
                    theSameGroup++;
                }
            }
            
            for (HashMap item : workbookGroups) {
                if (item.get(WORKBOOK_GROUPS_MAP_GROUP_INDEX).equals(groupMap.get(WORKBOOK_GROUPS_MAP_GROUP_INDEX))) {
                    if (!item.get(WORKBOOK_GROUPS_MAP_IS_PIVOT).equals(groupMap.get(WORKBOOK_GROUPS_MAP_IS_PIVOT)) && groupMap.get(WORKBOOK_GROUPS_MAP_IS_PIVOT).equals("true")) {
                        item.put(WORKBOOK_GROUPS_MAP_IS_PIVOT, groupMap.get(WORKBOOK_GROUPS_MAP_IS_PIVOT));
                        if ((Integer)item.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX) < (Integer)groupMap.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX)) {
                            item.put(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX, groupMap.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX));
                        }
                        if ((Integer)item.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX) > (Integer)groupMap.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX)) {
                            item.put(WORKBOOK_GROUPS_MAP_START_ROW_INDEX, groupMap.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX));
                        }
                        break;
                    } else {
                        if ((Integer)item.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX) < (Integer)groupMap.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX)) {
                            item.put(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX, groupMap.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX));
                        }
                        if ((Integer)item.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX) > (Integer)groupMap.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX)) {
                            item.put(WORKBOOK_GROUPS_MAP_START_ROW_INDEX, groupMap.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX));
                        }
                        break;
                    }
                } else if (theSameGroup <= 0) {
                    workbookGroups.add(groupMap);
                    break;
                }
            }
        }
        groupMap = null;
        theSameGroup = 0;
    }
    
    /**
     * Returns Cell in the sheet using column number and row number.
     * 
     * @param currentSheet
     * @param columnNum
     * @param rowNum
     * @return
     */
    public static Cell getCell(Sheet currentSheet, int columnNum, int rowNum) {
        return currentSheet.getCell(columnNum, rowNum);
    }
    
    /**
     * Method for parsing cell value for different formats.
     * 
     * @param cell
     * @param type
     * @param format
     * @return
     */
    public static String parseCellValue(Cell cell, String type, String format) {
        String cellContent = "";
        String sepArray = Consts.getEX().ROWS_SEPARATOR + "--" + Consts.getEX().APOSTROPHE;
        String replaceArray = Consts.getEX().ROWS_SEPARATOR_R + "--" + Consts.getEX().APOSTROPHE_R;
        
        if (!Common.isEmpty(cell.getContents())) {
            if (type.equals(Consts.getEX().NUMBER_TYPE)) {
                cellContent = Common.replaceSeparatorInString(cell.getContents(), ",", ".");
                
                if (!Common.isValidDoubleValue(cellContent)) {
                    cellContent = "";
                }
            } else if (type.equals(Consts.getEX().DATE_TYPE)) {
                try {
                    DateCell dateCell = (DateCell)cell;
                    DateFormat df = new SimpleDateFormat(format);
                    Date dateContent = dateCell.getDate();
                    cellContent = "to_date(" + "'" + df.format(dateContent) + "'" + ", " + "'" + format + "'" + ")";
                } catch (ClassCastException e) {
                    cellContent = "to_date(" + "'" + cellContent + "'" + ", " + "'" + format + "'" + ")";
                }
            } else if (type.equals(Consts.getEX().STRING_TYPE)) {
                cellContent = "'" + Common.replaceSepArrayInString(cell.getContents(), sepArray, replaceArray) + "'";
            }
        }

        return cellContent;
        
    }

    /**
     * Returns max rows count for group using Sheet and min start row.
     * 
     * @param sheet
     * @param group
     * @param minStartRow
     * @return
     */
    public static int getMaxRows(Sheet sheet, Element group, int minStartRow) {
        List<Integer> columnPositions = new ArrayList<Integer>();
        
        int curColumnPos = 0;
        int emptyRows = 0;
        int maxRows = 0;
        int sheetRows = sheet.getRows();
        
        NodeList groups = group.getElementsByTagName(Consts.getEX().XML_COLUMN_TAG);
        
        for (int i=0; i<groups.getLength(); i++) {
            Element column = (Element)groups.item(i);
            curColumnPos = Integer.parseInt(column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR));
            columnPositions.add(curColumnPos-1);
            column = null;
        }
        
        for (int j=minStartRow-1; j<sheetRows; j++) {
            for (int k : columnPositions) {
                if (sheet.getCell(k, j).getContents().isEmpty()) {
                    emptyRows++;
                }
            }
            if (emptyRows >= columnPositions.size()) {
                maxRows = j;
                break;
            }
            
            emptyRows = 0;
        }
        
        columnPositions = null;
        
        return (maxRows < sheetRows && maxRows > 0)?maxRows : sheetRows;
    }
    
    /**
     * Update max rows for reload data.
     * [Used in loadOnlyDataInExcel() and loadExcel()];
     * 
     * @param cubeStructure
     * @param sheetsInfo
     * @return
     */
    public static Document updateMaxRows(Document cubeStructure, List<HashMap> sheetsInfo) {
        // init parameters
        int maxRows = 1;
        int startRow = 1;
        String columnPos = "";
        String pivotPos = "";
        String groupSheet = "";
        // init first sheet
        Sheet currentSheet = (Sheet)sheetsInfo.get(0).get(SHEETS_INFO_MAP_SHEET);
        // get document element
        Element documentElement = cubeStructure.getDocumentElement();
        Element Groups = (Element)documentElement.getElementsByTagName(Consts.getEX().XML_GROUPS_TAG).item(0);
        NodeList groups = Groups.getElementsByTagName(Consts.getEX().XML_GROUP_TAG);

        for (int i=0; i<groups.getLength(); i++) {
            Element Group = (Element)groups.item(i);
            groupSheet = Group.getAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR);
            startRow = Integer.parseInt(Group.getAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR));
            
            // if sheet is not equal sheet name we start looking for appropriate sheet
            if (!currentSheet.getName().equals(groupSheet)) {
                for (HashMap t : sheetsInfo) {
                    if ((t.get(SHEETS_INFO_MAP_SHEET_NAME)).equals(groupSheet)) {
                        currentSheet = (Sheet)t.get(SHEETS_INFO_MAP_SHEET);
                        break;
                    }
                }
            }
            
            maxRows = getMaxRows(currentSheet, Group, startRow);
            
            Group.setAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR, String.valueOf(maxRows));
            maxRows = 1;
            startRow = 1;
        }
        
        columnPos = null;
        pivotPos = null;
        currentSheet = null;
        
        return cubeStructure;
    }
    
    /**
     * Returns list of HashMap objects and everyone of them contains all the attributes of columns on the configuration list:
     * - COLUMN_NUMBER;
     * - COLUMN_NAME;
     * - COLUMN_DESCRIPTION;
     * - COLUMN_TYPE;
     * - COLUMN_DB_TYPE;
     * - COLUMN_FORMAT;
     * - COLUMN_AGGR_RULE
     * - COLUMN_SHEET;
     * - COLUMN_DATA_GROUP;
     * - COLUMN_POSITION_IN_GROUP;
     * - COLUMN_PARENT_POSITION.
     * 
     * @param configSheet
     * @return List HashMap
     */
    public List<HashMap> getItemsList(Sheet configSheet) {
        
        List<HashMap> itemsList = new ArrayList<HashMap>();

        for (int i=Consts.getEX().CONFIGURATION_LIST_START_ROW; i<=configSheet.getRows(); i++) {
            HashMap item = new HashMap();
            String attrName = configSheet.getCell(Consts.getEX().CFG_PROPERTIES + i).getContents().trim();
            
            // firstly, we look for pivot column
            // if it exists we decide that this sheet is pivot
            /* 1. PIVOT ROW POSITION */
            Cell column = configSheet.getCell(Consts.getEX().CFG_PIVOT_ROW_POSITION + i); 
            
            if (column.getType() == CellType.NUMBER) {
                if (Integer.parseInt(column.getContents()) > 0) {
                    item.put(Consts.getEX().COLUMN_PIVOT_ROW_POSITION, Integer.parseInt(column.getContents()));
                } else {
                    item.put(Consts.getEX().COLUMN_PIVOT_ROW_POSITION, Consts.getEX().UNKNOWN_NUMBER);
                }
            } else {
                item.put(Consts.getEX().COLUMN_PIVOT_ROW_POSITION, Consts.getEX().UNKNOWN_NUMBER);
            }
            
            /* 2. SHEET NAME FOR COLUMN */
            column = configSheet.getCell(Consts.getEX().CFG_SHEET_NAME + i);
            if (column.getContents().trim().length() > 0) {
                item.put(Consts.getEX().COLUMN_SHEET, column.getContents().trim());
            } else {
                item.put(Consts.getEX().COLUMN_SHEET, Consts.getEX().UNKNOWN_STRING);
            }
            
            /* 3. DATA GROUP FOR COLUMN */
            column = configSheet.getCell(Consts.getEX().CFG_DATA_GROUP + i);
            if (column.getContents().trim().length() > 0 && column.getType() == CellType.NUMBER) {
                item.put(Consts.getEX().COLUMN_DATA_GROUP, Integer.parseInt(column.getContents()));
            } else {
                item.put(Consts.getEX().COLUMN_DATA_GROUP, Consts.getEX().UNKNOWN_NUMBER);
            }
            
            /* 4. POSITION IN GROUP FOR COLUMN */
            column = configSheet.getCell(Consts.getEX().CFG_POS_IN_GROUP + i); 
            if (attrName.equals(Consts.getEX().FACT_COLUMN)) {
                //@isadikov: dummy position in group number for facts
                if (column.getType() == CellType.NUMBER) {
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Integer.parseInt(column.getContents()));
                } else if (column.getType() == CellType.LABEL) {
                    // if it is pivot we put the first column index
                    // and for being unique if it is a pivot column we make position using [firstPosition * i * 99]
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Integer.parseInt(column.getContents().split(Consts.getEX().PIVOT_SPLIT_REGEX)[0])/* * i * 99*/);
                } else {
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Consts.getEX().UNKNOWN_NUMBER);
                }
            } else {
                if (column.getType() == CellType.NUMBER) {
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Integer.parseInt(column.getContents()));
                } else if (column.getType() == CellType.LABEL) {
                    // if it is pivot we put the first column index
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Integer.parseInt(column.getContents().split(Consts.getEX().PIVOT_SPLIT_REGEX)[0]));
                } else {
                    item.put(Consts.getEX().COLUMN_POSITION_IN_GROUP, Consts.getEX().UNKNOWN_NUMBER);
                }
            }
            
            /* 5. PARENT POSITION FOR COLUMN */
            //if parent position contains only numbers and "," we suppose that it's valid
            column = configSheet.getCell(Consts.getEX().CFG_PARENT_POS + i);
            // if parent position equals current column num we replace it with "0"
            if (column.getContents().equals(item.get(Consts.getEX().COLUMN_POSITION_IN_GROUP))) {
                item.put(Consts.getEX().COLUMN_PARENT_POSITION, defaultParentPosition);
            // if parent position equals empty cell we replace it with "0"   
            } else if (column.getContents().length() <= 0) {
                item.put(Consts.getEX().COLUMN_PARENT_POSITION, defaultParentPosition);
            } else if (!Common.isValidParentPos(column.getContents(), "" + item.get(Consts.getEX().COLUMN_POSITION_IN_GROUP))) {
                item.put(Consts.getEX().COLUMN_PARENT_POSITION, defaultParentPosition);         
            } else {
                item.put(Consts.getEX().COLUMN_PARENT_POSITION, Common.validParentPos(column.getContents()));
            }
            
            /* 6. START ROW FOR COLUMN */
            column = configSheet.getCell(Consts.getEX().CFG_START_ROW + i);
            if (column.getType() == CellType.NUMBER) {
                int columnStartRow = Integer.parseInt(column.getContents());
                item.put(Consts.getEX().COLUMN_START_ROW, columnStartRow);
            } else {
                item.put(Consts.getEX().COLUMN_START_ROW, Consts.getEX().UNKNOWN_NUMBER);
            }
            
            /* 7. COLUMN NAME */
            String columnName = configSheet.getCell(Consts.getEX().CFG_NAME + i).getContents().trim();
            if (columnName.length() > 0 && Common.isOnlyAllowedSymbols(columnName)) {
                item.put(Consts.getEX().COLUMN_NAME, Common.substrL(columnName, 0, Consts.getEX().COLUMN_NAME_MAX_LENGTH));
            } else {
                item.put(Consts.getEX().COLUMN_NAME, attrName.substring(0, 4));
            }
            
            /* 8. COLUMN DESCRIPTION */
            column = configSheet.getCell(Consts.getEX().CFG_DESCRIPTION + i);
            if (column.getContents() == null || column.getContents().length() <= 0) {
                //@isadikov: if description is null we replace it with column name
                //item.put(Consts.getEX().COLUMN_DESCRIPTION, Consts.getEX().UNKNOWN_STRING + i);
                item.put(Consts.getEX().COLUMN_DESCRIPTION, item.get(Consts.getEX().COLUMN_NAME));
            } else {
                item.put(Consts.getEX().COLUMN_DESCRIPTION, column.getContents().trim());
            }
            
            /* 9. COLUMN TYPE AND COLUMN FORMAT */
            Cell formatColumn = configSheet.getCell(Consts.getEX().CFG_FORMAT + i);
            Cell typeColumn = configSheet.getCell(Consts.getEX().CFG_TYPE + i);
            
            if (typeColumn.getContents().equals(Consts.getEX().STRING_EXCEL_TYPE)) {
                item.put(Consts.getEX().COLUMN_TYPE, Consts.getEX().STRING_TYPE);
                item.put(Consts.getEX().COLUMN_FORMAT, Consts.getEX().STRING_FORMAT_TYPE);
            } else if (typeColumn.getContents().equals(Consts.getEX().DATE_TYPE)) {
                item.put(Consts.getEX().COLUMN_TYPE, Consts.getEX().DATE_TYPE);
                item.put(Consts.getEX().COLUMN_FORMAT, formatColumn.getContents().trim());
            } else if (typeColumn.getContents().equals(Consts.getEX().NUMBER_TYPE)) {
                item.put(Consts.getEX().COLUMN_TYPE, Consts.getEX().NUMBER_TYPE);
                if (formatColumn.getContents().trim().equals(Consts.getEX().NUMBER_DOUBLE_MASK)) {
                    item.put(Consts.getEX().COLUMN_FORMAT, Consts.getEX().NUMBER_DOUBLE_TYPE);
                } else if (formatColumn.getContents().trim().equals(Consts.getEX().NUMBER_INT_MASK)) {
                    item.put(Consts.getEX().COLUMN_FORMAT, Consts.getEX().NUMBER_INT_TYPE);
                }
            } else {
                item.put(Consts.getEX().COLUMN_TYPE, Consts.getEX().UNKNOWN_STRING);
                item.put(Consts.getEX().COLUMN_FORMAT, Consts.getEX().UNKNOWN_STRING);
            }
            
            /* 10. COLUMN DATABASE TYPE (USING COLUMN TYPE AND COLUMN FORMAT) */
            if (typeColumn.getContents().equals(Consts.getEX().STRING_EXCEL_TYPE)) {
                item.put(Consts.getEX().COLUMN_DB_TYPE, item.get(Consts.getEX().COLUMN_TYPE) + "(" + item.get(Consts.getEX().COLUMN_FORMAT) + ")");
            } else if (typeColumn.getContents().equals(Consts.getEX().DATE_TYPE)) {
                item.put(Consts.getEX().COLUMN_DB_TYPE, item.get(Consts.getEX().COLUMN_TYPE));
            } else if (typeColumn.getContents().equals(Consts.getEX().NUMBER_TYPE)) {
                item.put(Consts.getEX().COLUMN_DB_TYPE, item.get(Consts.getEX().COLUMN_TYPE) + "(" + item.get(Consts.getEX().COLUMN_FORMAT) + ")");
            }
            
            /* 11. COLUMN AGGREGATION RULE */
            String ag = configSheet.getCell(Consts.getEX().CFG_AGG_RULE + i).getContents().trim();
            if (ag.equals(Consts.getEX().AGGR_SUM_TYPE)|| ag.equals(Consts.getEX().AGGR_MIN_TYPE) || ag.equals(Consts.getEX().AGGR_MAX_TYPE) || ag.equals(Consts.getEX().AGGR_AVG_TYPE) || ag.equals(Consts.getEX().AGGR_CNT_TYPE)) {
                item.put(Consts.getEX().COLUMN_AGGR_RULE, ag);
            } else {
                item.put(Consts.getEX().COLUMN_AGGR_RULE, Consts.getEX().UNKNOWN_STRING);
            }
            
            /* 12. DIMENSION FOR COLUMN */
            if (attrName.equals(Consts.getEX().ATTRIBUTE_COLUMN)) {
                item.put(Consts.getEX().DIMENSION, Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY);
            } else if (attrName.equals(Consts.getEX().FACT_COLUMN)) {
                if (item.get(Consts.getEX().COLUMN_AGGR_RULE).equals(Consts.getEX().UNKNOWN_STRING)) {
                    item.put(Consts.getEX().DIMENSION, Consts.getEX().FACT_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY);
                } else {
                    item.put(Consts.getEX().DIMENSION, Consts.getEX().FACT_TYPE + Consts.getEX().MEASURE_PROPERTY);
                }
            }
            
            /* 13. COLUMN UNIQUE CODE */
            //@isadikov: it is used to set the unique column code across the workbook
            // we do not load this item part, but we use it when load data
            String uniqueCode = "" + (String)item.get(Consts.getEX().COLUMN_SHEET) 
                                + "-" + (String)item.get(Consts.getEX().COLUMN_NAME) 
                                + "-" + (Integer)item.get(Consts.getEX().COLUMN_DATA_GROUP)
                                + "-" + i + "";
            item.put(Consts.getEX().COLUMN_UNIQUE_CODE, uniqueCode);
            uniqueCode = null;
            
            /* 14. COLUMN_COMPLEX_GROUP */
            // columns for load (in case if it is pivot we load whole string)
            // we use the same column as we used in data position load, but it is an int[] array instead
            column = configSheet.getCell(Consts.getEX().CFG_POS_IN_GROUP + i); 
            
            if (column.getType() == CellType.NUMBER) {
                int[] temp = { Integer.parseInt(column.getContents()) };
                item.put(Consts.getEX().COLUMN_COMPLEX_GROUP, temp);
            } else if (column.getType() == CellType.LABEL) {
                String[] temp = column.getContents().split(Consts.getEX().PIVOT_SPLIT_REGEX);
                int[] tempInt = new int[temp.length];
                
                for (int k=0; k<temp.length; k++) {
                    tempInt[k] = Integer.parseInt(temp[k]);
                }
                
                item.put(Consts.getEX().COLUMN_COMPLEX_GROUP, tempInt);
            } else {
                int[] temp = { Consts.getEX().UNKNOWN_NUMBER };
                item.put(Consts.getEX().COLUMN_COMPLEX_GROUP, temp);
            }
            
            /* 15. GROUP INDEXES LIST [workbookGroups] */
            int cMinStartRow = (Integer)item.get(Consts.getEX().COLUMN_START_ROW);
            
            int group = (Integer)item.get(Consts.getEX().COLUMN_DATA_GROUP);
            int cMaxRows = 1;
            
            /* we define max rows later with updateMaxRowsForLoad() method
            String groupSheet = (String)item.get(Consts.getEX().COLUMN_SHEET);
            for (HashMap t : sheetsInfo) {
                if ((t.get(SHEETS_INFO_MAP_SHEET_NAME)).equals(groupSheet)) {
                    cMaxRows = ((Sheet)t.get(SHEETS_INFO_MAP_SHEET)).getColumn((Integer)item.get(Consts.getEX().COLUMN_POSITION_IN_GROUP) - 1).length;
                    break;
                }
            }
            */
            
            getWorkbookGroups(workbookGroups, group, (Integer)item.get(Consts.getEX().COLUMN_PIVOT_ROW_POSITION), cMaxRows, cMinStartRow);
            
            itemsList.add(item);
            item = null;
            attrName = null;
        }
        
        return itemsList;
    }

    /**
     * Method returns loadStructure document using load methods :
     * loadPivotCellsStringFromSheet() and loadCommonCellsStringFromSheet().
     * 
     * @param cubeStructure
     * @param loadStructure
     * @param sheetsInfo
     * @return
     */
    public static Document loadTableData(Document cubeStructure, Document loadStructure, List<HashMap> sheetsInfo) {
        Element Structure = cubeStructure.getDocumentElement();
        // get groups list
        NodeList groups = ((Element)Structure.getElementsByTagName(Consts.getEX().XML_GROUPS_TAG).item(0)).getElementsByTagName(Consts.getEX().XML_GROUP_TAG);
        Element group = cubeStructure.createElement(Consts.getEX().XML_GROUP_TAG);
        
        // get load structure document element
        Element LoadStructure = loadStructure.getDocumentElement();
        Element loadGroups = (Element)LoadStructure.getElementsByTagName(Consts.getEX().XML_GROUPS_TAG).item(0);
        
        Sheet currentSheet = (Sheet)sheetsInfo.get(0).get(SHEETS_INFO_MAP_SHEET);
        
        for (int i=0; i<groups.getLength(); i++) {
            group = (Element)groups.item(i);
            
            Element loadGroup = loadStructure.createElement(Consts.getEX().XML_GROUP_TAG);
            
            if (!group.getAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR).equals(currentSheet.getName())) {
                for (HashMap item : sheetsInfo) {
                    if (group.getAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR).equals(item.get(SHEETS_INFO_MAP_SHEET_NAME))) {
                        currentSheet = (Sheet)item.get(SHEETS_INFO_MAP_SHEET);
                        break;
                    }
                }
            }
            
            if (group.getAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR).equals("false")) {
                loadGroup = loadCommonCellsStringFromSheet(loadStructure, group, currentSheet);
            } else {
                loadGroup = loadPivotCellsStringFromSheet(loadStructure, group, currentSheet);
            }
            
            loadGroups.appendChild(loadGroup);
            loadGroup = null;
        }
        
        
        return loadStructure;
    }

    /**
     * Method to define pivot type. If it is a common type return true, false otherwise.
     * Pivot common type is when attributes above facts.
     * 
     * @param dimPivotPos
     * @param factsPivotPos
     * @return
     */
    public static boolean isPivotCommonType(String dimPivotPos, List<String> factsPivotPos) {
        int max = factsPivotPos.size();
        String res = "";
        
        for (int i=0; i<max; i++) {
            res += factsPivotPos.get(i).split(",")[0] + ",";
        }
        
        String res1 = "";
        for (int i=0; i<max; i++) {
            res1 += dimPivotPos.split(",")[i] + ",";
        }
        
        if (res.equals(res1)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Method for empty row defining.
     * If row is empty we use it as row break or end of table.
     * 
     * @param row
     * @return
     */
    public static boolean isEmptyRowForBreak(Element row) {
        NodeList values = row.getElementsByTagName(Consts.getEX().XML_VALUE_TAG);
        int flag = 0;
        
        for (int i=0; i<values.getLength(); i++) {
            Element currentValue = (Element)values.item(i);
            if (currentValue.getTextContent().isEmpty()) {
                flag++;
            }
        }
        
        if (flag >= values.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for pivot table group loading.
     * 
     * @param loadStructure
     * @param group
     * @param sheet
     * @return
     */
    public static Element loadPivotCellsStringFromSheet(Document loadStructure, Element group, Sheet sheet) {
        int startRow = Integer.parseInt(group.getAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR)) - 1;
        int maxRows = Integer.parseInt(group.getAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR)) - 1;
        
        int[] firstPivotColumnsComplexGroup = { 1 };
        int pivotMaxFactCount = 0;
        int pivotMaxDimCount = 0;
        String firstPivotUniqueCode = "";
        
        NodeList columns = group.getElementsByTagName(Consts.getEX().XML_COLUMN_TAG);
        
        // 1. Define pivot parameters: max fact counts and first pivot unique code
        List<HashMap> globalPivotElements = new ArrayList<HashMap>();
        List<String> pivotFactColumnsComplexGroups = new ArrayList<String>();
        String pivotDimColumnsComplexGroup = "";
        boolean isCommonPivotStructure = true;
            
        for (int i=0; i<columns.getLength(); i++) {
            Element column = (Element)columns.item(i);
            
            String pivotPos = (column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR).isEmpty())?column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR) : column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR);
            String[] pivotElementPosStr = pivotPos.split(Consts.getEX().PIVOT_SPLIT_REGEX);
            int[] pivotElementPos = Common.convertToIntArray(pivotElementPosStr);
            
            if (pivotElementPos.length > 1 && !column.getAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                pivotMaxFactCount++;
                pivotFactColumnsComplexGroups.add(pivotPos);
            }
            
            if (pivotElementPos.length > 1 && column.getAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                firstPivotColumnsComplexGroup = pivotElementPos;
                firstPivotUniqueCode = (firstPivotUniqueCode.isEmpty())?column.getAttribute(Consts.getEX().XML_COLUMN_UNIQUE_CODE_ATTR) : firstPivotUniqueCode;
                
                pivotDimColumnsComplexGroup = pivotPos;
            }
            // 2. Define global pivot elements group
            if (pivotElementPos.length > 1 && column.getAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                List<HashMap> pivotElements = new ArrayList<HashMap>();
                
                int rowNum = Integer.parseInt(column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_ROW_POS_ATTR)) - 1;
                String tempCellContents = "";
                for (int k : pivotElementPos) {
                    Cell cell = getCell(sheet, k-1, rowNum);
                    String cellContents = parseCellValue(cell, column.getAttribute(Consts.getEX().XML_COLUMN_TYPE_ATTR), column.getAttribute(Consts.getEX().XML_COLUMN_FORMAT_ATTR));
                    
                    HashMap pivotElementMap = new HashMap();
                    
                    if (!cellContents.isEmpty()) {
                        pivotElementMap.put(PIVOT_ELEMENT_MAP_KEY, k);
                        pivotElementMap.put(PIVOT_ELEMENT_MAP_VALUE, cellContents);
                        
                        tempCellContents = cellContents;
                    } else {
                        pivotElementMap.put(PIVOT_ELEMENT_MAP_KEY, k);
                        pivotElementMap.put(PIVOT_ELEMENT_MAP_VALUE, tempCellContents);
                    }
                    pivotElements.add(pivotElementMap);
                    pivotElementMap = null;
                }
                HashMap pivotUniqueMap = new HashMap();
                pivotUniqueMap.put(column.getAttribute(Consts.getEX().XML_COLUMN_UNIQUE_CODE_ATTR), pivotElements);
                
                globalPivotElements.add(pivotUniqueMap);
            }
            
            column = null;
        }
        
        // @isadikov: define pivot table group type
        isCommonPivotStructure = isPivotCommonType(pivotDimColumnsComplexGroup, pivotFactColumnsComplexGroups);
        pivotDimColumnsComplexGroup = null;
        pivotFactColumnsComplexGroups = null;
        
        // 3. create load data elements and add to the Group element
        pivotMaxDimCount = ((List<HashMap>)globalPivotElements.get(0).get(firstPivotUniqueCode)).size();
        
        String cellContents = "";
        int factMeasureIndex = 0;
        
        Element Group = loadStructure.createElement(Consts.getEX().XML_GROUP_TAG);
        Group.setAttribute(Consts.getEX().XML_GROUP_NAME_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_NAME_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR));
        
        for (int i=startRow; i<=maxRows; i++) {
            
            for (int j=1; j<=((List<HashMap>)globalPivotElements.get(0).get(firstPivotUniqueCode)).size(); j++) {
                factMeasureIndex = 0;
                
                Element Row = loadStructure.createElement(Consts.getEX().XML_ROW_TAG);
                Row.setAttribute(Consts.getEX().XML_ROW_NUMBER_ATTR, String.valueOf(j));
                
                for (int k=0; k<columns.getLength(); k++) {    
                    Element column = (Element)columns.item(k);
                    
                    String uniqueCode = column.getAttribute(Consts.getEX().XML_COLUMN_UNIQUE_CODE_ATTR);
                    
                    String pivotPos = (column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR).isEmpty())?column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR) : column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR);
                    String[] pivotElementPosStr = pivotPos.split(Consts.getEX().PIVOT_SPLIT_REGEX);
                    int[] pivotElementPos = new int[pivotElementPosStr.length];
                    for (int d=0; d<pivotElementPosStr.length; d++) {
                        pivotElementPos[d] = Integer.parseInt(pivotElementPosStr[d]);
                    }
                    
                    if (pivotElementPos.length <= 1 && column.getAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                        int columnNum = Integer.parseInt(column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR)) - 1;
                        
                        try {
                            Cell cell = getCell(sheet, columnNum, i);
                            cellContents = parseCellValue(cell, column.getAttribute(Consts.getEX().XML_COLUMN_TYPE_ATTR), column.getAttribute(Consts.getEX().XML_COLUMN_FORMAT_ATTR));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            cellContents = "";
                        }
                        
                        Element Value = loadStructure.createElement(Consts.getEX().XML_VALUE_TAG);
                        Value.setAttribute(Consts.getEX().XML_VALUE_NAME_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_NAME_ATTR));
                        Value.setAttribute(Consts.getEX().XML_VALUE_ABS_POS_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_ABS_COLUMN_POS_ATTR));
                        Value.setTextContent(cellContents);
                        
                        Row.appendChild(Value);
                        Value = null;
                        
                    } else if (pivotElementPos.length > 1 && column.getAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                        for (HashMap tempPivot : globalPivotElements) {
                            if (tempPivot.containsKey(uniqueCode)) {
                                HashMap pivotElement = ((List<HashMap>)tempPivot.get(uniqueCode)).get(j-1);
                                
                                Element Value = loadStructure.createElement(Consts.getEX().XML_VALUE_TAG);
                                Value.setAttribute(Consts.getEX().XML_VALUE_NAME_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_NAME_ATTR));
                                Value.setAttribute(Consts.getEX().XML_VALUE_ABS_POS_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_ABS_COLUMN_POS_ATTR));
                                Value.setTextContent((String)pivotElement.get(PIVOT_ELEMENT_MAP_VALUE));
                                
                                Row.appendChild(Value);
                                Value = null;
                                break;
                            }
                        }
                    } else {
                        String columnPivotPos = (column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR).isEmpty())?column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR) : column.getAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR);
                        String[] columnNumsStr = columnPivotPos.split(Consts.getEX().PIVOT_SPLIT_REGEX);
                        int[] columnNums = new int[columnNumsStr.length];
                        for (int d=0; d<columnNumsStr.length; d++) {
                            columnNums[d] = Integer.parseInt(columnNumsStr[d]);
                        }
                        
                        int columnNum = firstPivotColumnsComplexGroup[j-1];    
                        
                        if (Common.contains(columnNums, columnNum + factMeasureIndex)) {
                            Cell cell = getCell(sheet, columnNum + factMeasureIndex - 1, i);
                            cellContents = parseCellValue(cell, column.getAttribute(Consts.getEX().XML_COLUMN_TYPE_ATTR), column.getAttribute(Consts.getEX().XML_COLUMN_FORMAT_ATTR));
                            
                            Element Value = loadStructure.createElement(Consts.getEX().XML_VALUE_TAG);
                            Value.setAttribute(Consts.getEX().XML_VALUE_NAME_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_NAME_ATTR));
                            Value.setAttribute(Consts.getEX().XML_VALUE_ABS_POS_ATTR, column.getAttribute(Consts.getEX().XML_COLUMN_ABS_COLUMN_POS_ATTR));
                            Value.setTextContent(cellContents);
                            
                            Row.appendChild(Value);
                            Value = null;
                            
                            if (isCommonPivotStructure) {
                                factMeasureIndex++;
                            } else {
                                factMeasureIndex = factMeasureIndex + pivotMaxDimCount/pivotMaxFactCount;    
                            }
                        }
                    }
                }
                
                if (isCommonPivotStructure) {
                    j = j + pivotMaxFactCount - 1;
                } else {
                    if (j == pivotMaxDimCount/pivotMaxFactCount) {
                        j = j + pivotMaxDimCount;
                    }
                }
                
                Group.appendChild(Row);
                Row = null;
            }
            
        }
        
        return Group;
    }

    /**
     * Method for common table group loading.
     * 
     * @param loadStructure
     * @param group
     * @param sheet
     * @return
     */
    public static Element loadCommonCellsStringFromSheet(Document loadStructure, Element group, Sheet sheet) {
        int startRow = Integer.parseInt(group.getAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR)) - 1;
        int maxRows = Integer.parseInt(group.getAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR)) - 1;
        
        // initialize parameters
        Cell cell;
        String cellContents = "";
        int columnNum = -1;
        String columnType = "";
        String columnFormat = "";
        String columnName = "";
        String columnAbsPos = "";
        
        NodeList columns = group.getElementsByTagName(Consts.getEX().XML_COLUMN_TAG);
        
        Element Group = loadStructure.createElement(Consts.getEX().XML_GROUP_TAG);
        Group.setAttribute(Consts.getEX().XML_GROUP_NAME_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_NAME_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR));
        Group.setAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR, group.getAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR));
       
        for (int i=startRow; i<=maxRows; i++) {
            Element Row = loadStructure.createElement(Consts.getEX().XML_ROW_TAG);
            Row.setAttribute(Consts.getEX().XML_ROW_NUMBER_ATTR, String.valueOf(i));
            
            for (int j=0; j<columns.getLength(); j++) {
                Element column = (Element)columns.item(j);
                columnNum = Integer.parseInt(column.getAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR)) - 1;
                columnType = column.getAttribute(Consts.getEX().XML_COLUMN_TYPE_ATTR);
                columnFormat = column.getAttribute(Consts.getEX().XML_COLUMN_FORMAT_ATTR);
                columnName = column.getAttribute(Consts.getEX().XML_COLUMN_NAME_ATTR);
                columnAbsPos = column.getAttribute(Consts.getEX().XML_COLUMN_ABS_COLUMN_POS_ATTR);
                
                try {
                    cell = getCell(sheet, columnNum, i);
                    cellContents = parseCellValue(cell, columnType, columnFormat);
                } catch (ArrayIndexOutOfBoundsException e) {
                    cellContents = "";
                }
                
                Element Value = loadStructure.createElement(Consts.getEX().XML_VALUE_TAG);
                Value.setAttribute(Consts.getEX().XML_VALUE_NAME_ATTR, columnName);
                Value.setAttribute(Consts.getEX().XML_VALUE_ABS_POS_ATTR, columnAbsPos);
                Value.setTextContent(cellContents);
                
                Row.appendChild(Value);
                
                Value = null;
            }
            
            if (isEmptyRowForBreak(Row)) {
                break;
            } else {
                Group.appendChild(Row);
            }
        }
        
        return Group;
   }
    
    /**
     * Main method to load and parse excel file and return string of operation status.
     * 
     * @param file
     * @return
     */
    public String loadExcel(InputStream file) {
        try {
            WorkbookSettings ws = new WorkbookSettings();
            System.setProperty("file.encoding", Consts.getEX().ENCODE_STRING);
            ws.setEncoding(Consts.getEX().ENCODE_STRING);
            ws.setCharacterSet(0);
            
            Workbook workbook = this.getExcelInstance(file, ws);
            
            configSheet = workbook.getSheet(Consts.getEX().CONFIGURATION_SHEET);
            
            if (!hasConfigurationSheet(configSheet)) {
                return Consts.getMSG().KMSG_ERROR_EXCEL_CONFIGURATION_MISSED;
            } else {
                if (!hasAllSheets(workbook, configSheet)) { 
                    return Consts.getMSG().KMSG_ERROR_EXCEL_SHEET_DECLARED_BUT_NOT_EXISTED;
                } else {
                    sheetsInfo = getSheetsInfoInWorkbook(workbook, configSheet);
                    
                    List<HashMap> Columns = this.getItemsList(configSheet);
                    
                    // if order is important for download, we can switch it on:
                    if (isOrderUsed == true) {
                        List<HashMap> factColumns = new ArrayList<HashMap>();
                        List<HashMap> attrColumns = new ArrayList<HashMap>();
                            
                        for (HashMap item : Columns) {
                            if (item.get(Consts.getEX().DIMENSION).equals(Consts.getEX().ATTRIBUTE_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY)) {
                                attrColumns.add(item);
                            } else if (
                                item.get(Consts.getEX().DIMENSION).equals(Consts.getEX().FACT_TYPE + Consts.getEX().ATTRIBUTE_PROPERTY) 
                                || item.get(Consts.getEX().DIMENSION).equals(Consts.getEX().FACT_TYPE + Consts.getEX().MEASURE_PROPERTY)
                            ) {
                                factColumns.add(item);
                            }
                        }
                        GlobalColumnsList.addAll(factColumns);
                        GlobalColumnsList.addAll(attrColumns);
                        
                        factColumns = null;
                        attrColumns = null;
                    } else {
                        GlobalColumnsList.addAll(Columns);
                    }
                    
                    //check list consistency
                    if (this.isCompletedSuccessfully(GlobalColumnsList).equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                        
                        // 1. Start forming groups structure
                        Document cubeStructure = this.generateExcelStructureDOM();
                        cubeStructure = this.fillExcelStructureDOM(cubeStructure, Columns, workbookGroups);
                        Document loadStructure = this.generateExcelLoadStructureDOM();
                        // @isadikov: now we update rows count for groups, because rows count may be not equal to sheet.getRows();
                        cubeStructure = updateMaxRows(cubeStructure, sheetsInfo);
                        
                        // 2. load data to XML
                        loadStructure = loadTableData(cubeStructure, loadStructure, sheetsInfo);
                        
                        ExcelData.put(Consts.getEX().COLUMNS_INFO, Common.DomToXMLString(cubeStructure));    
                        ExcelData.put(Consts.getEX().CELLS, Common.DomToXMLString(loadStructure));
                        
                        cubeStructure = null;    
                        loadStructure = null;
                        dealloc();
                            
                        return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
                    } else {
                        dealloc();
                        return this.isCompletedSuccessfully(GlobalColumnsList);
                    }
                }
            }
        } catch (IOException e) {
            dealloc();
            return "#IOException# " + e.toString();
        } catch (BiffException e) {
            dealloc();
            return "#BiffException# " + e.toString();
        } catch (Exception e) {
            dealloc();
            e.printStackTrace();
            return "#Exception# " + e.toString();        
        }
    }
    
    /**
     * Second main method to load and parse excel file and return string of operation status.
     * Used only for load data to DB (without structure rebuild)
     * 
     * @param file
     * @return
     */
    public String loadOnlyDataInExcel(String pmName, InputStream file) {
        try {
            List<String> res = DBImport.getModelStructureFromDB(pmName);
            
            if (res.get(0).equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                WorkbookSettings ws = new WorkbookSettings();
                System.setProperty("file.encoding", Consts.getEX().ENCODE_STRING);
                ws.setEncoding(Consts.getEX().ENCODE_STRING);
                ws.setCharacterSet(0);
                
                Workbook workbook = this.getExcelInstance(file, ws);
                
                configSheet = workbook.getSheet(Consts.getEX().CONFIGURATION_SHEET);
                sheetsInfo = getSheetsInfoInWorkbook(workbook, configSheet);
                
                if (!hasConfigurationSheet(configSheet)) {
                    return "#ExcelException# Configuration is missed";
                } else {
                    //ExcelData.put(Consts.getEX().COLUMNS_INFO, null);
                    String xmlStructure = res.get(1);
                    Document cubeStructure = Common.generateDom(xmlStructure);
                    xmlStructure = "";
                    // update rows count
                    cubeStructure = updateMaxRows(cubeStructure, sheetsInfo);
                    
                    Document loadStructure = this.generateExcelLoadStructureDOM();
                    
                    loadStructure = loadTableData(cubeStructure, loadStructure, sheetsInfo);
                    
                    ExcelData.put(Consts.getEX().CELLS, Common.DomToXMLString(loadStructure));
                    
                    cubeStructure = null;    
                    loadStructure = null;
                    dealloc();
                    
                    return Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA;
                }
            } else {
                dealloc();
                return res.get(0) + " - " + res.get(1);
            }
        } catch (IOException e) {
            dealloc();
            return "#IOException# " + e.toString();
        } catch (BiffException e) {
            dealloc();
            return "#BiffException# " + e.toString();
        } catch (Exception e) {
            dealloc();
            return "#Exception# " + e.toString();        
        }
    }
    
    // Methods for XML
    /**
     * Generates cube structure XML.
     * 
     * @return
     */
    public static Document generateExcelStructureDOM() {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Structure xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><" + Consts.getEX().XML_GROUPS_TAG + "></" + Consts.getEX().XML_GROUPS_TAG + "></Structure>";
        return Common.generateDom(xmlstring);
    }

    /**
     * Load cube structure XML.
     * 
     * @param d
     * @param globalColumnsList
     * @param workbookGroups
     * @return
     */
    public static Document fillExcelStructureDOM(Document d, List<HashMap> globalColumnsList, List<HashMap> workbookGroups) {
        String sepArray = Consts.getEX().COLUMNS_SEPARATOR + "--" + Consts.getEX().COLUMNS_LINE_SEPARATOR + "--" + Consts.getEX().APOSTROPHE;
        String replaceArray = Consts.getEX().COLUMNS_SEPARATOR_R + "--" + Consts.getEX().COLUMNS_LINE_SEPARATOR_R + "--" + Consts.getEX().APOSTROPHE_R;
        int absoluteColumnIndex = 1;
        int absoluteColumnInGroupIndex = 1;
        String sheetName = "";
        
        for (HashMap indexMap : workbookGroups) {
            int index = (Integer)indexMap.get(WORKBOOK_GROUPS_MAP_GROUP_INDEX);
            String isPivotStatus = (String)indexMap.get(WORKBOOK_GROUPS_MAP_IS_PIVOT);
            int maxRows = (Integer)indexMap.get(WORKBOOK_GROUPS_MAP_MAX_ROWS_INDEX);
            int minStartRows = (Integer)indexMap.get(WORKBOOK_GROUPS_MAP_START_ROW_INDEX);
            
            Element Group = d.createElement(Consts.getEX().XML_GROUP_TAG);
            
            for (HashMap item : globalColumnsList) {
                if (item.get(Consts.getEX().COLUMN_DATA_GROUP).equals(index)) {
                    Element Column = d.createElement(Consts.getEX().XML_COLUMN_TAG);
                    // 1. column name
                    Column.setAttribute(Consts.getEX().XML_COLUMN_NAME_ATTR, Common.replaceSepArrayInString((String)item.get(Consts.getEX().COLUMN_NAME), sepArray, replaceArray));
                    // 2. column desc
                    Column.setAttribute(Consts.getEX().XML_COLUMN_DESC_ATTR, Common.replaceSepArrayInString((String)item.get(Consts.getEX().COLUMN_DESCRIPTION), sepArray, replaceArray));
                    // 3. column db type
                    Column.setAttribute(Consts.getEX().XML_COLUMN_CTYPE_ATTR, (String)item.get(Consts.getEX().COLUMN_DB_TYPE));
                    // 4. aggregate type
                    Column.setAttribute(Consts.getEX().XML_COLUMN_AGGTYPE_ATTR, (String)item.get(Consts.getEX().COLUMN_AGGR_RULE));
                    // 5. column position in the group
                    Column.setAttribute(Consts.getEX().XML_COLUMN_COLUMN_POS_ATTR, String.valueOf(item.get(Consts.getEX().COLUMN_POSITION_IN_GROUP)));
                    // 6. column parent position
                    Column.setAttribute(Consts.getEX().XML_COLUMN_PARENT_POS_ATTR, (String)item.get(Consts.getEX().COLUMN_PARENT_POSITION));
                    // 7. absolute column position
                    Column.setAttribute(Consts.getEX().XML_COLUMN_ABS_COLUMN_POS_ATTR, String.valueOf(absoluteColumnIndex++));
                    // 8. pivot columns position
                    Column.setAttribute(Consts.getEX().XML_COLUMN_PIVOT_POS_ATTR, Common.validPivotColumns((int[])item.get(Consts.getEX().COLUMN_COMPLEX_GROUP), Consts.getEX().PIVOT_SPLIT_REGEX));
                    // 9. pivot row position
                    Column.setAttribute(Consts.getEX().XML_COLUMN_PIVOT_ROW_POS_ATTR, ((item.get(Consts.getEX().COLUMN_PIVOT_ROW_POSITION).equals(Consts.getEX().UNKNOWN_NUMBER))?"" : String.valueOf(item.get(Consts.getEX().COLUMN_PIVOT_ROW_POSITION))));
                    // 10. column dimension
                    Column.setAttribute(Consts.getEX().XML_COLUMN_DIM_ATTR, (String)item.get(Consts.getEX().DIMENSION));
                    // 11. column type
                    Column.setAttribute(Consts.getEX().XML_COLUMN_TYPE_ATTR, (String)item.get(Consts.getEX().COLUMN_TYPE));
                    // 12. column format
                    Column.setAttribute(Consts.getEX().XML_COLUMN_FORMAT_ATTR, (String)item.get(Consts.getEX().COLUMN_FORMAT));
                    // 13. column unique code
                    Column.setAttribute(Consts.getEX().XML_COLUMN_UNIQUE_CODE_ATTR, (String)item.get(Consts.getEX().COLUMN_UNIQUE_CODE));
                    // 14. absolute position in group
                    Column.setAttribute(Consts.getEX().XML_COLUMN_ABS_GROUP_POS_ATTR, String.valueOf(absoluteColumnInGroupIndex++));
                    
                    if (sheetName.length() <= 0) {
                        sheetName = (String)item.get(Consts.getEX().COLUMN_SHEET);
                    } else if (!sheetName.equals(item.get(Consts.getEX().COLUMN_SHEET))){
                        sheetName = (String)item.get(Consts.getEX().COLUMN_SHEET);
                    }
                    
                    Group.appendChild(Column);
                    Column = null;
                }
            }
            
            Group.setAttribute(Consts.getEX().XML_GROUP_NAME_ATTR, "" + index + "");
            Group.setAttribute(Consts.getEX().XML_GROUP_IS_PIVOT_ATTR, isPivotStatus);
            Group.setAttribute(Consts.getEX().XML_GROUP_MAX_ROWS_ATTR, "" + maxRows + "");
            Group.setAttribute(Consts.getEX().XML_GROUP_MIN_START_ROW_ATTR, "" + minStartRows + "");
            Group.setAttribute(Consts.getEX().XML_GROUP_SHEET_ATTR, sheetName);
            
            d.getElementsByTagName(Consts.getEX().XML_GROUPS_TAG).item(0).appendChild(Group);
            Group = null;
            absoluteColumnInGroupIndex = 1;
        }
        
        return d;
    }

    /**
     * Generate load structure XML.
     * 
     * @return
     */
    public static Document generateExcelLoadStructureDOM() {
        String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><LoadStructure xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><" + Consts.getEX().XML_GROUPS_TAG + "></" + Consts.getEX().XML_GROUPS_TAG + "></LoadStructure>";
        return Common.generateDom(xmlstring);
    }
    
    public void dealloc() {
        GlobalColumnsList.clear();
        sheetsInfo.clear();
        workbookGroups.clear();
        //ExcelData = null;
        configSheet = null;
    }
}