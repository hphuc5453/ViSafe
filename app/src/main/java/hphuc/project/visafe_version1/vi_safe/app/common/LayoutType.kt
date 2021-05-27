package hphuc.project.visafe_version1.vi_safe.app.common

enum class LayoutType(var value:String) {
    LAYOUT_PHASE("LAYOUT_PHASE"),
    LAYOUT_PRICE("LAYOUT_PRICE"),
    LAYOUT_TAB("LAYOUT_TAB"),
    LAYOUT_STEP("LAYOUT_STEP"),
    LAYOUT_TASK("LAYOUT_TASK"),
    LAYOUT_HEADER_TASK("LAYOUT_HEADER_TASK"),
    LAYOUT_REQUEST("LAYOUT_REQUEST")
}
/*
LAYOUT_TEMPLATE:
 - ’LAYOUT_PHASE’ : layout list các phase.
     +json data theo : LEGAL_PROJECT_1 , DESIGN_DASHBOARD_2

 - ‘LAYOUT_PRICE’ : layout list các bước báo giá.
    + json data theo : DESIGN_PLANNING_2,3,5,6,8,9,11,12

 - ‘LAYOUT_TAB’ : layout list dạng tab.
    + json data theo : LEGAL_PROJECT_8, DESIGN_ IN_HOUSE_DESIGN_1, DESIGN_ CONSULTANT_1, DESIGN_ LOCAL_CONSULTANT_1

 - ‘LAYOUT_STEP’: layout dạng step.
     + json data theo :LEGAL_PROJECT_ 2,3,5,7, DESIGN_ PLANNING_1

- ‘LAYOUT_TASK’: layout list task cha con.
     + json data theo : DESIGN_BUDGET_2,  DESIGN_ IN_HOUSE_DESIGN_2, DESIGN_ CONSULTANT_2, INTERNAL_TASK _2, LEGAL_PROJECT_8
**/