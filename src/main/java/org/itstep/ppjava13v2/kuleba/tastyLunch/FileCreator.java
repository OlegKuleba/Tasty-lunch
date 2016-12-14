package org.itstep.ppjava13v2.kuleba.tastyLunch;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itstep.ppjava13v2.kuleba.tastyLunch.constants.AppConstants;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class FileCreator implements AppConstants {

    public static final int USER_NAME_COL = 0;
    public static final int DISH_DAY_COL = 0;
    public static final int DISH_NAME_COL = 1;
    public static final int DISH_COST_COL = 2;
    public static final int DISH_PORTION_COL = 3;
    public static final int DISH_AMOUNT_COL = 3;
    public static final int DISH_SUMMARY_COST_COL = 4;

    public static final int MONDAY_OFFSET = 0;
    public static final int TUESDAY_OFFSET = 4;
    public static final int WEDNESDAY_OFFSET = 8;
    public static final int THURSDAY_OFFSET = 12;
    public static final int FRIDAY_OFFSET = 16;

    public static final String USER_NAME_HEADER = "NAME";
    public static final String DISH_NAME_HEADER = "DISH";
    public static final String DISH_COST_HEADER = "COST";
    public static final String DISH_PORTION_HEADER = "PORTION";

    public static void writeToUsersOrdersFile(List<Order> orderList, ServletContext context) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(FILE_NAME_FOR_ORDERS);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.LESS_DOTS);

        Row row = sheet.createRow(0);
        row.setRowStyle(style);
        Cell cell = row.createCell(USER_NAME_COL);
        cell.setCellValue(USER_NAME_HEADER);
        cell.setCellStyle(style);

        setHeaders(row, MONDAY_OFFSET, style);
        setHeaders(row, TUESDAY_OFFSET, style);
        setHeaders(row, WEDNESDAY_OFFSET, style);
        setHeaders(row, THURSDAY_OFFSET, style);
        setHeaders(row, FRIDAY_OFFSET, style);

        int rowIndex = 1;
        row = sheet.createRow(rowIndex);

        for (Order order : orderList) {
            rowIndex = drawSelector(row, sheet, style);
            row = sheet.createRow(++rowIndex);
            cell = row.createCell(USER_NAME_COL);
            cell.setCellValue(order.getUser().getLoginEmail());

            for (Dish dish : order.getDishList()) {
                if (dish.getDayOfWeek() == Dish.DaysOfWeek.MONDAY) {
                    if (null == row.getCell(USER_NAME_COL) || !order.getUser().getLoginEmail().equals(row.getCell(USER_NAME_COL).getStringCellValue())) {
                        rowIndex = revertCaret(row, sheet, USER_NAME_COL, MONDAY_OFFSET);
                        row = sheet.getRow(rowIndex);
                    }
                    writeCells(row, MONDAY_OFFSET, dish);
                }

                if (dish.getDayOfWeek() == Dish.DaysOfWeek.TUESDAY) {
                    if (null == row.getCell(USER_NAME_COL) || !order.getUser().getLoginEmail().equals(row.getCell(USER_NAME_COL).getStringCellValue())) {
                        rowIndex = revertCaret(row, sheet, USER_NAME_COL, TUESDAY_OFFSET);
                        row = sheet.getRow(rowIndex);
                    }
                    writeCells(row, TUESDAY_OFFSET, dish);
                }

                if (dish.getDayOfWeek() == Dish.DaysOfWeek.WEDNESDAY) {
                    if (null == row.getCell(USER_NAME_COL) || !order.getUser().getLoginEmail().equals(row.getCell(USER_NAME_COL).getStringCellValue())) {
                        rowIndex = revertCaret(row, sheet, USER_NAME_COL, WEDNESDAY_OFFSET);
                        row = sheet.getRow(rowIndex);
                    }
                    writeCells(row, WEDNESDAY_OFFSET, dish);
                }

                if (dish.getDayOfWeek() == Dish.DaysOfWeek.THURSDAY) {
                    if (null == row.getCell(USER_NAME_COL) || !order.getUser().getLoginEmail().equals(row.getCell(USER_NAME_COL).getStringCellValue())) {
                        rowIndex = revertCaret(row, sheet, USER_NAME_COL, THURSDAY_OFFSET);
                        row = sheet.getRow(rowIndex);
                    }
                    writeCells(row, THURSDAY_OFFSET, dish);
                }

                if (dish.getDayOfWeek() == Dish.DaysOfWeek.FRIDAY) {
                    if (null == row.getCell(USER_NAME_COL) || !order.getUser().getLoginEmail().equals(row.getCell(USER_NAME_COL).getStringCellValue())) {
                        rowIndex = revertCaret(row, sheet, USER_NAME_COL, FRIDAY_OFFSET);
                        row = sheet.getRow(rowIndex);
                    }
                    writeCells(row, FRIDAY_OFFSET, dish);
                }
                rowIndex++;
                if (sheet.getRow(rowIndex) == null) {
                    row = sheet.createRow(rowIndex);
                } else {
                    row = sheet.getRow(rowIndex);
                }
            }
        }
        sheet.autoSizeColumn(USER_NAME_COL);
        sheet.autoSizeColumn(DISH_NAME_COL);
        sheet.autoSizeColumn(DISH_NAME_COL + TUESDAY_OFFSET);
        sheet.autoSizeColumn(DISH_NAME_COL + WEDNESDAY_OFFSET);
        sheet.autoSizeColumn(DISH_NAME_COL + THURSDAY_OFFSET);
        sheet.autoSizeColumn(DISH_NAME_COL + FRIDAY_OFFSET);

        createAndWriteFile(workbook, context, FILE_NAME_FOR_ORDERS);
    }

    private static void createAndWriteFile(Workbook workbook, ServletContext context, String fileName) {
        String path = context.getRealPath("") + File.separator + DIRECTORY_FOR_REPORTS_FILES + File.separator;
        File ordersDirectory = new File(path);
        if (!ordersDirectory.exists()) {
            ordersDirectory.mkdir();
        }

        try {
            workbook.write(new FileOutputStream(new File(ordersDirectory, fileName + EXCEL_FILE_EXTENSION)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCells(Row row, int offset, Dish dish) {
        Cell cell;
        cell = row.createCell(DISH_NAME_COL + offset);
        cell.setCellValue(dish.getName());

        cell = row.createCell(DISH_COST_COL + offset);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(dish.getCost().toEngineeringString());

        cell = row.createCell(DISH_PORTION_COL + offset);
        cell.setCellValue(dish.getPortion());
    }

    private static int revertCaret(Row row, Sheet sheet, int userColumn, int offset) {
        int rowIndex = row.getRowNum();
        Cell cell;
        if (rowIndex > 0) {
            do {
                rowIndex--;
                row = sheet.getRow(rowIndex);
                cell = row.getCell(userColumn);
                if (null != row.getCell(DISH_NAME_COL + offset)) {// || !row.getCell(DISH_NAME_COL + offset).getStringCellValue().equals("")
                    return ++rowIndex;
                }
            }
            while (null == cell && rowIndex > 0); //!name.equals(cell.getStringCellValue()) &&
        }
        return rowIndex;
    }

    private static void setHeaders(Row row, int offset, CellStyle style) {
        Cell cell;
        cell = row.createCell(DISH_NAME_COL + offset);
        cell.setCellValue(DISH_NAME_HEADER);
        cell.setCellStyle(style);
        cell = row.createCell(DISH_COST_COL + offset);
        cell.setCellValue(DISH_COST_HEADER);
        cell.setCellStyle(style);
        cell = row.createCell(DISH_PORTION_COL + offset);
        cell.setCellValue(DISH_PORTION_HEADER);
        cell.setCellStyle(style);
    }

    private static int drawSelector(Row row, Sheet sheet, CellStyle style) {
        Cell cell;

        int rowIndex = row.getRowNum();
        rowIndex++;
        row = sheet.createRow(rowIndex);

        cell = row.createCell(DISH_NAME_COL + MONDAY_OFFSET);
        cell.setCellValue(Dish.DaysOfWeek.MONDAY.toString());
        cell.setCellStyle(style);
        cell = row.createCell(DISH_NAME_COL + TUESDAY_OFFSET);
        cell.setCellValue(Dish.DaysOfWeek.TUESDAY.toString());
        cell.setCellStyle(style);
        cell = row.createCell(DISH_NAME_COL + WEDNESDAY_OFFSET);
        cell.setCellValue(Dish.DaysOfWeek.WEDNESDAY.toString());
        cell.setCellStyle(style);
        cell = row.createCell(DISH_NAME_COL + THURSDAY_OFFSET);
        cell.setCellValue(Dish.DaysOfWeek.THURSDAY.toString());
        cell.setCellStyle(style);
        cell = row.createCell(DISH_NAME_COL + FRIDAY_OFFSET);
        cell.setCellValue(Dish.DaysOfWeek.FRIDAY.toString());
        cell.setCellStyle(style);
        row.setRowStyle(style);
        return rowIndex;
    }

    public static void writeToDishesAmountFile(List<Dish> dishList, List<Integer> amountOfEachDish, ServletContext context) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(FILE_NAME_FOR_DISHES);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.LESS_DOTS);

        int count = 0;
        count = writeCellsForDishes(sheet, style, count, dishList, amountOfEachDish, Dish.DaysOfWeek.MONDAY);
        count = writeCellsForDishes(sheet, style, count, dishList, amountOfEachDish, Dish.DaysOfWeek.TUESDAY);
        count = writeCellsForDishes(sheet, style, count, dishList, amountOfEachDish, Dish.DaysOfWeek.WEDNESDAY);
        count = writeCellsForDishes(sheet, style, count, dishList, amountOfEachDish, Dish.DaysOfWeek.THURSDAY);
        writeCellsForDishes(sheet, style, count, dishList, amountOfEachDish, Dish.DaysOfWeek.FRIDAY);

        sheet.autoSizeColumn(DISH_DAY_COL);
        sheet.autoSizeColumn(DISH_NAME_COL);

        createAndWriteFile(workbook, context, FILE_NAME_FOR_DISHES);
    }

    private static int writeCellsForDishes(Sheet sheet, CellStyle style, int count, List<Dish> dishList, List<Integer> amountOfEachDish, Dish.DaysOfWeek day) {

        boolean isDaySelectorDraw = false;
        Cell cell;
        Dish dish = null;
        int amount;
        int countForRows = sheet.getLastRowNum() + 1;
        BigDecimal cost;
        Row row;

        while (count < dishList.size() && dishList.get(count).getDayOfWeek().equals(day)) {

            row = sheet.createRow(countForRows);
            dish = dishList.get(count);

            if (!isDaySelectorDraw) {
                row.setRowStyle(style);
                cell = row.createCell(DISH_DAY_COL);
                cell.setCellValue(dish.getDayOfWeek().toString());
                cell.setCellStyle(style);
                isDaySelectorDraw = true;
                countForRows++;
                row = sheet.createRow(countForRows);
            }

            amount = amountOfEachDish.get(count);
            cost = dish.getCost();

            cell = row.createCell(DISH_NAME_COL);
            cell.setCellValue(dish.getName());
            cell = row.createCell(DISH_COST_COL);
            cell.setCellValue(cost.toString());
            cell = row.createCell(DISH_AMOUNT_COL);
            cell.setCellValue(amount);
            cell = row.createCell(DISH_SUMMARY_COST_COL);
            cell.setCellValue((cost.multiply(new BigDecimal(amount))).toString());

            countForRows++;
            count++;
        }
        return count;
    }

}
