package framework.all.util.listutil

import above.RunWeb
import all.util.ListUtil
import wss.pages.element.MainMenuElement

class UtListUtil extends RunWeb {
    def test() {

        setup('vdiachuk', 'List Util unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test list util utility',
                 'PBI: 0',
                 'logLevel:info'])

        assert ListUtil.getElementsByParameter([], "first") == []
        assert ListUtil.getElementsByParameter([], "last") == []
        assert ListUtil.getElementsByParameter([], "all") == []

        assert ListUtil.getElementsByParameter([1, 2, 3], "first") == [1]
        assert ListUtil.getElementsByParameter([1, 2, 3], "last") == [3]
        assert ListUtil.getElementsByParameter([1, 2, 3], "random").size() == 1
        assert [1, 2, 3].contains(ListUtil.getElementsByParameter([1, 2, 3], "random")[0])

        List stList = ["aaaaa", "bbbbb", "cccccc", "ddddd"]
        assert ListUtil.getElementsByParameter(stList, "first") == ["aaaaa"]
        assert ListUtil.getElementsByParameter(stList, "all") == stList
        log ListUtil.getElementsByParameter(stList, "random")
    }
}
