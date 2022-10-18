package above.report

/**
 *      Report Email Template
 *      (!) No exceptions handling
 *      @author akudin
 */
class ReportEmailTemplate {

    private List body = []
    final static Map fontColors = [
            green: '#008000',
            greenLight: '#EEFFEE'
    ]


    /**
     * Generate HTML template
     */
    String getHtml() {

        String html = '<html><body>'
        body.each {
            html += '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tbody><tr>'
            switch (it.type) {
                case 'columns':
                    it.columns.eachWithIndex { col, idx ->
                        if (it.data[idx] instanceof ReportEmailTemplateTable) {
                            html += "<td  style=\"vertical-align:top\" width=\"$col\">${it.data[idx].getHtml()}</td>"
                        } else {
                            html += "<td  style=\"vertical-align:top\" width=\"$col\">${it.data[idx]}</td>"
                        }
                    }
                    break
                case 'space':
                    html += '<td style="height: 25px;">&nbsp;</td>'
                    break
                case 'title':
                    if (it.title instanceof ReportEmailTemplateTable) {
                        html += "<td style=\"font-family: Arial; ${it.style}\">${it.title.getHtml()}</td>"
                    } else {
                        html += "<td style=\"font-family: Arial; ${it.style}\">${it.title}</td>"
                    }
                    break
                default:
                    throw new Exception("Wrong table part ${it}")
                    break
            }
            html += '</tr></tbody></table>'
        }
        html += '</body></html>'
        return html
    }


    /** Column data adding */
    void addToColumn(Integer columnNumber, columnData) {
        body[body.size()-1].data[columnNumber-1] = columnData
    }


    /** Columns adding */
    void addColumns(String template) {
        Map column = [
                type: 'columns',
                columns: template.split(java.util.regex.Pattern.quote('|')),
                data: null
        ]
        column.data = Collections.nCopies(column.columns.size(), '&nbsp;').toList()
        body << column
    }


    /** Space adding */
    void addSpace() {
        body << [
                type: 'space'
        ]
    }


    /**
     * Title row adding
     */
    void addTitle(title, String style = '') {
        if (title instanceof String) {
            title = title.replace('\n', '<br />')
        }
        body << [
                type: 'title',
                title: title,
                style: applyColors(style)
        ]
    }


    /** Apply colors */
    private String applyColors(String style) {
        fontColors.keySet().each {
            style = style.replace("%$it%", fontColors[it])
        }
        return style
    }

}
