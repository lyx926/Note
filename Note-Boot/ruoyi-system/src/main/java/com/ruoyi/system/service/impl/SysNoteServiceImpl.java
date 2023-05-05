package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysNoteMapper;
import com.ruoyi.system.domain.SysNote;
import com.ruoyi.system.service.ISysNoteService;

/**
 * 笔记Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
@Service
public class SysNoteServiceImpl implements ISysNoteService 
{
    @Autowired
    private SysNoteMapper sysNoteMapper;

    /**
     * 查询笔记
     * 
     * @param noteId 笔记主键
     * @return 笔记
     */
    @Override
    public SysNote selectSysNoteByNoteId(Long noteId)
    {
        return sysNoteMapper.selectSysNoteByNoteId(noteId);
    }

    /**
     * 查询笔记列表
     * 
     * @param sysNote 笔记
     * @return 笔记
     */
    @Override
    public List<SysNote> selectSysNoteList(SysNote sysNote)
    {
        return sysNoteMapper.selectSysNoteList(sysNote);
    }

    /**
     * 新增笔记
     * 
     * @param sysNote 笔记
     * @return 结果
     */
    @Override
    public int insertSysNote(SysNote sysNote)
    {
        sysNote.setCreateTime(DateUtils.getNowDate());
        return sysNoteMapper.insertSysNote(sysNote);
    }

    /**
     * 修改笔记
     * 
     * @param sysNote 笔记
     * @return 结果
     */
    @Override
    public int updateSysNote(SysNote sysNote)
    {
        sysNote.setUpdateTime(DateUtils.getNowDate());
        return sysNoteMapper.updateSysNote(sysNote);
    }

    /**
     * 批量删除笔记
     * 
     * @param noteIds 需要删除的笔记主键
     * @return 结果
     */
    @Override
    public int deleteSysNoteByNoteIds(Long[] noteIds)
    {
        return sysNoteMapper.deleteSysNoteByNoteIds(noteIds);
    }

    /**
     * 删除笔记信息
     * 
     * @param noteId 笔记主键
     * @return 结果
     */
    @Override
    public int deleteSysNoteByNoteId(Long noteId)
    {
        return sysNoteMapper.deleteSysNoteByNoteId(noteId);
    }
}
