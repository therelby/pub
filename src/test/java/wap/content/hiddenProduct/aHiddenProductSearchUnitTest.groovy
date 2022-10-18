package wap.content.hiddenProduct

import above.Execute

Execute.suite([remoteBrowser: false], [
        new UtWAPHiddenProductSearch(),
        new UtWAPHpSearchFunction(),
        new UtWAPHpUpdateFunction()
], 1)