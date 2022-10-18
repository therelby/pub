package above.report

/**
 *      Tables for ReportEmailTemplate
 *      @author akudin
 */
class ReportEmailTemplateTable {

    private String title
    private String titleStyle
    private Boolean titleInTable
    private List body = []


    ReportEmailTemplateTable(String columnsTemplate, String title = '', String style = '', Boolean titleInTable = true) {
        this.title = title
        this.titleStyle = style
        this.titleInTable = titleInTable
        body << columnsTemplate.split(java.util.regex.Pattern.quote('|'))
    }


    /** Generate HTML */
    String getHtml() {

        String html = ''
        if (!titleInTable) {
            html += '<table border="0" cellspacing="0" cellpadding="10"><tbody><tr><td ' +
                    'style="font-family: Arial; ' + titleStyle + '">' +
                    title + '</td></tr></tbody></table>'
        }

        html += '<table border="1" cellspacing="0" cellpadding="10" width="100%" style="border-color:#EEFFEE;"><tbody>'
        if (titleInTable) {
            html += '<tr><td colspan="' + body[0].size() + '" style="font-family: Arial; ' + titleStyle + '">' +
                    title + '</td></tr>'
        }
        body.eachWithIndex { it, index ->
            if (index > 0) {
                html += '<tr>'
                it.eachWithIndex { data, idx ->
                    def addStyle = ''
                    if (data instanceof Integer || (data instanceof String && data.length() > 0 && data[data.length()-1].isNumber())) {
                        addStyle = 'text-align:right;'
                    }
                    if (data instanceof Integer) {
                        data = ReportDailyData.numberToFormattedString(data)
                    }
                    html += '<td style="font-family: Arial; font-size: 13pt; ' + addStyle + '" width="' +
                            body[0][idx] + '">' + data + '</td>'
                }
                html += '</tr>'
            }
        }
        html += '</tbody></table>'
        return html
    }


    /** Add table row data */
    void addRow(List data) {
        body << data
    }

}
