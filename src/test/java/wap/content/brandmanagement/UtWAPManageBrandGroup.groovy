package wap.content.brandmanagement

import above.RunWeb
import wap.common.content.brandmanagement.brandgrouplisting.WAPManageBrandGroup
import wap.common.WAPLogin

class UtWAPManageBrandGroup extends RunWeb {

    def userName
    def password

    UtWAPManageBrandGroup(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Manage Brand Group Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap manage brand group',
                 'PBI: 0',
                 'logLevel:info'])

        WAPManageBrandGroup wapManageBrandGroup = new WAPManageBrandGroup(userName,password)

        loadPage(getUrl("adminportal"))

        String parentName = "Regency"
        List children = ["Regency Tables & Sinks", "Regency Space Solutions", "Regency Plumbing & Hardware", "Regency Mobile Products", "Regency Gas Hoses"]
        List randomBrands = ["Baso", "US Foods"]
        assert wapManageBrandGroup.loadManageBrandGroupPage(10013)

        // UI
        assert wapManageBrandGroup.isHeaderPresent()
        assert wapManageBrandGroup.isParentDropdownPresent()
        assert wapManageBrandGroup.isChildDropdownPresent()
        assert wapManageBrandGroup.isSubmitButtonPresent()

        assert wapManageBrandGroup.getHeaderText().trim() == wapManageBrandGroup.pageHeaderText
        assert wapManageBrandGroup.getParentDropdownTitle().trim() == wapManageBrandGroup.parentDropdownText
        assert wapManageBrandGroup.getChildDropdownTitle().trim() == wapManageBrandGroup.childDropdownText
        assert wapManageBrandGroup.getSubmitButtonText().trim() == wapManageBrandGroup.editGroupButtonText

        // DATA
        assert wapManageBrandGroup.getParentBrand() == parentName
        assert wapManageBrandGroup.getChildBrands() == children
        assert wapManageBrandGroup.getAllAvailableVendors() != []
        assert wapManageBrandGroup.getDropdownOptions("Parent").contains(parentName)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.getDropdownOptions("Child") == children

        // FUNCTIONALITY
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.enterText("Parent", "Supercalifragilisticexpialidocious")
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.enterText("Child", "Supercalifragilisticexpialidocious")

        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandText("Parent", "Food Handler", true)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandText("Child", "Food Handler", true)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandText("Parent", "Food Handler", false)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandText("Child", "Food Handler", false)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Parent", "Food Handler", 0)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Child", "Food Handler", 0)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Parent", "Food Handler", 1)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Child", "Food Handler", 1)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Parent", "Food Handler", 2)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Child", "Food Handler", 2)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Parent", "Food Handler", 3)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.setBrandTextByStringLength("Child", "Food Handler", 3)
        refresh()
        waitForPage(5)
        assert !wapManageBrandGroup.setBrandTextByStringLength("Parent", "Food Handler", 20)
        refresh()
        waitForPage(5)
        assert !wapManageBrandGroup.setBrandTextByStringLength("Child", "Food Handler", 20)

        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrand("Child", "Baso", true)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Parent", "Baso")
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Parent", "Baso", 1)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Parent", "Baso", 2)
        refresh()
        waitForPage(5)
        assert !wapManageBrandGroup.addBrandByStringLength("Parent", "Baso", 5)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Child", "Baso")
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Child", "Baso", 1)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addBrandByStringLength("Child", "Baso", 2)
        refresh()
        waitForPage(5)
        assert !wapManageBrandGroup.addBrandByStringLength("Child", "Baso", 5)
        refresh()
        waitForPage(5)
        assert wapManageBrandGroup.addMultipleChildBrands(randomBrands)

        wapManageBrandGroup.loadManageBrandGroupPage(2319)
        assert wapManageBrandGroup.addBrand("Parent", "Baso", true)
        assert wapManageBrandGroup.addBrand("Child", "US Foods", true)
        assert tryClick(wapManageBrandGroup.headerTitleXpath)
        assert wapManageBrandGroup.clickSaveButton(false)

    }
}
