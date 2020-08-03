package com.app.htmlcomponents;

import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.exceptions.QAFException;
import com.qaf.utils.QAFPageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithHeaders extends QAFElement {
    private static final Logger logger = Logger.getLogger(TableWithHeaders.class);
    Map<String, Integer> columnMap = null;

    public TableWithHeaders(WebDriver driver, With with, String value) {
        super(driver, with, value);
        QAFPageFactory.initElements(this);
    }

    private void initColumnsMap() {
        if (null != columnMap)
            return;
        logger.info("Initializing column data.");
        List<QAFElement> colElements = this.findElements(With.xpath, "./thead/tr/th");
        if (colElements.size() == 0)
            throw new QAFException("Could not located columns in the table");
        int columnNum = 0;
        columnMap = new HashMap<>();
        for (QAFElement ele : colElements)
            columnMap.put(ele.getText().trim(), ++columnNum);
    }

    public int getColumnCount() {
        initColumnsMap();
        return columnMap.size();
    }

    public List<String> getColumnNames() {
        initColumnsMap();
        return new ArrayList<>(columnMap.keySet());
    }

    public int getRowsCount() {
        return this.findElements(With.xpath, "./tbody/tr").size();
    }

}
