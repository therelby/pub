package framework.all.util

import above.Run
import above.RunWeb
import all.util.DoubleUtil
import all.util.StringUtil


class UtDoubleUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'all.util.DoubleUtil  Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 "tfsTcIds:265471",
                 'keywords:unit test find findElement', 'logLevel:debug'])

        RunWeb r = run()

        Double a = 2.111
        Double b = 2.2
        Double c = 2.112

        //negative
        Double na = -3.111
        Double nb = -3.2
        Double nc = -3.11

        assert DoubleUtil.equalWithPrecision(a, b, 0.15)
        assert DoubleUtil.equalWithPrecision(a, c)
        assert ! DoubleUtil.equalWithPrecision(a,b,0.01)

        //negative check
        assert DoubleUtil.equalWithPrecision(na, nb, 0.1)
        assert DoubleUtil.equalWithPrecision(na, nc)
        assert ! DoubleUtil.equalWithPrecision(na,nb,0.01)

    }
}
