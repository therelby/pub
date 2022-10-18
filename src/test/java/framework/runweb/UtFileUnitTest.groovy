package framework.runweb

import above.RunWeb
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

class UtFileUnitTest extends RunWeb {


    def test() {

        setup('vdiachuk', 'File Unit test | Framework Self Testing Tool',
                ['product:wss',
                 'tfsProject:Webstaurant.StoreFront',
                 "tfsTcIds:265471",
                 'keywords:unit test find findElement',
                 'logLevel:info'])



        String url = 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf'
        String fileName = 'test.pdf'
        String fileContent = 'Dummy PDF file'

        //Checking with saving to test.pdf file name
        def path = downloadFile(url, fileName)
        //Loading an existing document
        File file = new File(path);
        PDDocument document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();
        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        log("file content$text");
        assert text.contains(fileContent)

        //checking with parsing name from Url
        path = downloadFile(url)
        //Loading an existing document
        file = new File(path);
        document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        pdfStripper = new PDFTextStripper();
        //Retrieving text from PDF document
        text = pdfStripper.getText(document);

        assert text.contains(fileContent)
        document.close();

    }

}
