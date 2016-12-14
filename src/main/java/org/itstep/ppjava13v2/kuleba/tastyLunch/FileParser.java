package org.itstep.ppjava13v2.kuleba.tastyLunch;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FileParser {

    public static final int DISH_NAME_COLUMN_NUMBER = 0;
    public static final int DISH_COST_COLUMN_NUMBER = 1;
    public static final int DISH_PORTION_COLUMN_NUMBER = 2;
    public static final int DISH_CATEGORY_COLUMN_NUMBER = 3;
    public static final int DISH_DESCRIPTION_COLUMN_NUMBER = 4;

    public static List<Dish> parse(String name) {

        InputStream in;
        Workbook workbook = null;
        Sheet sheet;

        char endOfFile = name.charAt(name.length() - 1);
        try {
            in = new FileInputStream(name);
            if (endOfFile == 'x') {
                workbook = new XSSFWorkbook(in);
            } else {
                workbook = new HSSFWorkbook(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cell nameCell;
        Cell costCell;
        Cell portionCell;
        Cell categoryCell;
        Cell descriptionCell;
        List<Dish> dishList = new ArrayList<>();
        Dish dish = null;
        String dayOfTheWeek;

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

            sheet = workbook.getSheetAt(i);
            System.out.println("NumberOfRows = " + sheet.getPhysicalNumberOfRows());
            dayOfTheWeek = Dish.DaysOfWeek.values()[i].toString();
            System.out.println("dayOfTheWeek = " + dayOfTheWeek);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) {
                rows.next();
            }

            if (rows.hasNext()) {
                while (rows.hasNext()) {
                    Row row = rows.next();
                    nameCell = row.getCell(DISH_NAME_COLUMN_NUMBER);
                    costCell = row.getCell(DISH_COST_COLUMN_NUMBER);
                    portionCell = row.getCell(DISH_PORTION_COLUMN_NUMBER);
                    categoryCell = row.getCell(DISH_CATEGORY_COLUMN_NUMBER);
                    descriptionCell = row.getCell(DISH_DESCRIPTION_COLUMN_NUMBER);
                    if (nameCell != null) {
                        dish = new Dish();
                        dish.setDayOfWeek(Dish.DaysOfWeek.values()[i]);
                        dish.setName(nameCell.getStringCellValue());
                        if (costCell != null) {
                            switch (costCell.getCellType()) {
                                case Cell.CELL_TYPE_NUMERIC:
                                    dish.setCost(new BigDecimal(costCell.getNumericCellValue()));
                                    break;
                                default:
                                    dish.setCost(new BigDecimal(Double.parseDouble(costCell.getStringCellValue())));
                                    break;
                            }
                        }
                        if (portionCell != null) {
                            switch (portionCell.getCellType()) {
                                case Cell.CELL_TYPE_NUMERIC:
                                    dish.setPortion("" + portionCell.getNumericCellValue());
                                    break;
                                default:
                                    dish.setPortion(portionCell.getStringCellValue());
                                    break;
                            }
                        }
                        if (categoryCell != null) {
                            dish.setCategory(categoryCell.getStringCellValue());
                        }
                        if (descriptionCell != null && !"".equals(descriptionCell.getStringCellValue())) {
                            dish.setDescription(descriptionCell.getStringCellValue());
                        }
                        dish.setDateStartOfNextWeek();
                        dishList.add(dish);
                    }
                }
            }
        }
        return dishList;
    }

    public String getStringFromProperties(Locale locale, String key, ServletContext context) {

        FileInputStream inputStream;
        Properties properties = new Properties();

        String resultStringFromProperties;
        StringBuilder path = new StringBuilder(new File(context.getRealPath("")) + File.separator);
        path.append("WEB-INF").append(File.separator).append("classes").append(File.separator)
                .append(getFileName(locale.toString()));

        try {
            inputStream = new FileInputStream(path.toString());
            properties.load(inputStream);
            resultStringFromProperties = properties.getProperty(key);
            inputStream.close();
        } catch (IOException e) {
            resultStringFromProperties = properties.getProperty("email.notProvideAccess");
            e.printStackTrace();
        }
        return resultStringFromProperties;
    }

    private String getFileName(String locale) {
        String file;

        if (locale.matches("^uk(\\S)*")) {
            file = "messages_uk.properties";
        } else if (locale.matches("^ru\\S*")) {
            file = "messages_ru.properties";
        } else {
            file = "messages_en.properties";
        }
        return file;
    }

}
