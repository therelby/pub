package framework.wss.pages.element.filter

import above.Execute

Execute.suite([
        new UtFilterFoldExpandClassification(),
        new UtFilterShowMoreCollapse(),
        new UtFilter(),
        new UtFilter2Types(),
        new UtFilterSearchWithin(),
        new UtFilterCategories(),

])