package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysNote;
import com.ruoyi.system.service.ISysNoteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 笔记Controller
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
@RestController
@RequestMapping("/system/note")
public class SysNoteController extends BaseController
{
    @Autowired
    private ISysNoteService sysNoteService;

    /**
     * 查询笔记列表
     */
    @PreAuthorize("@ss.hasPermi('system:note:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNote sysNote)
    {
        startPage();
        List<SysNote> list = sysNoteService.selectSysNoteList(sysNote);
        return getDataTable(list);
    }

    /**
     * 导出笔记列表
     */
    @PreAuthorize("@ss.hasPermi('system:note:export')")
    @Log(title = "笔记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysNote sysNote)
    {
        List<SysNote> list = sysNoteService.selectSysNoteList(sysNote);
        ExcelUtil<SysNote> util = new ExcelUtil<SysNote>(SysNote.class);
        util.exportExcel(response, list, "笔记数据");
    }

    /**
     * 获取笔记详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:note:query')")
    @GetMapping(value = "/{noteId}")
    public AjaxResult getInfo(@PathVariable("noteId") Long noteId)
    {
        return success(sysNoteService.selectSysNoteByNoteId(noteId));
    }

    /**
     * 新增笔记
     */
    @PreAuthorize("@ss.hasPermi('system:note:add')")
    @Log(title = "笔记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNote sysNote)
    {
        return toAjax(sysNoteService.insertSysNote(sysNote));
    }

    /**
     * 修改笔记
     */
    @PreAuthorize("@ss.hasPermi('system:note:edit')")
    @Log(title = "笔记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNote sysNote)
    {
        return toAjax(sysNoteService.updateSysNote(sysNote));
    }

    /**
     * 删除笔记
     */
    @PreAuthorize("@ss.hasPermi('system:note:remove')")
    @Log(title = "笔记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noteIds}")
    public AjaxResult remove(@PathVariable Long[] noteIds)
    {
        return toAjax(sysNoteService.deleteSysNoteByNoteIds(noteIds));
    }
}
