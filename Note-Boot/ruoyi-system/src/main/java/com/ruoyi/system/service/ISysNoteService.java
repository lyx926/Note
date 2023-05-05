package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNote;

/**
 * 笔记Service接口
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
public interface ISysNoteService 
{
    /**
     * 查询笔记
     * 
     * @param noteId 笔记主键
     * @return 笔记
     */
    public SysNote selectSysNoteByNoteId(Long noteId);

    /**
     * 查询笔记列表
     * 
     * @param sysNote 笔记
     * @return 笔记集合
     */
    public List<SysNote> selectSysNoteList(SysNote sysNote);

    /**
     * 新增笔记
     * 
     * @param sysNote 笔记
     * @return 结果
     */
    public int insertSysNote(SysNote sysNote);

    /**
     * 修改笔记
     * 
     * @param sysNote 笔记
     * @return 结果
     */
    public int updateSysNote(SysNote sysNote);

    /**
     * 批量删除笔记
     * 
     * @param noteIds 需要删除的笔记主键集合
     * @return 结果
     */
    public int deleteSysNoteByNoteIds(Long[] noteIds);

    /**
     * 删除笔记信息
     * 
     * @param noteId 笔记主键
     * @return 结果
     */
    public int deleteSysNoteByNoteId(Long noteId);
}
