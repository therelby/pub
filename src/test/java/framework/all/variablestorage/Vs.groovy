package framework.all.variablestorage

import above.RunWeb
import all.VariableStorage

class Vs extends RunWeb {

    def tcId = 265465 // debugging testcase
    def tcss = [270071, 296881]

    def test() {

        setup('akudin', 'Web Concept Unit Test | Azure DevOps API Handling', [
                'product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
                "tfsTcIds:$tcId,${tcss.join(',')}",
        ])

        assert !VariableStorage.set('wrong-way-variable-name', 'value')

        assert VariableStorage.delete('akudin-vs-unit-test')

        assert VariableStorage.set('akudin-vs-unit-test', null)
        assert VariableStorage.get('akudin-vs-unit-test') == ''

        assert VariableStorage.set('akudin-vs-unit-test', '')
        assert VariableStorage.get('akudin-vs-unit-test') == ''

        assert VariableStorage.set('akudin-vs-unit-test', 'Some text')
        assert VariableStorage.get('akudin-vs-unit-test') == 'Some text'

        assert VariableStorage.setData('akudin-vs-unit-test', null)
        assert VariableStorage.getData('akudin-vs-unit-test') == []

        assert VariableStorage.setData('akudin-vs-unit-test', [1, 2, 3])
        assert VariableStorage.getData('akudin-vs-unit-test') == [1, 2, 3]

        assert VariableStorage.setData('akudin-vs-unit-test', ['three': 3])
        assert VariableStorage.getData('akudin-vs-unit-test') == ['three': 3]

        assert VariableStorage.set('akudin-vs-unit-test', 'Some text', true)
        assert VariableStorage.setData('akudin-vs-unit-test', [1, 2, 3], true)

        assert VariableStorage.delete('akudin-vs-unit-test')
    }

}
