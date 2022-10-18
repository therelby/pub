package framework.wss.api.catalogauthoring.virtualgrouping

import above.Execute

Execute.suite([
        new UtVirtualGroupsApi(),
        new UtVirtualGroupSaveProductsApi()
])
