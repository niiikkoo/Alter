package org.alter.api.cfg.scripts

class ScriptList {
    fun script_1(): ScriptTypeList1<Int> = ScriptTypeList1(1)
    fun script_2(arg: Boolean): ScriptTypeList2<Int, Boolean> = ScriptTypeList2(2, arg)
    fun script_3(arg: Int): ScriptTypeList2<Int, Int> = ScriptTypeList2(3, arg)
    fun script_4(arg: String, arg2: Boolean): ScriptTypeList3<Int, String, Boolean> = ScriptTypeList3(4, arg, arg2)

}