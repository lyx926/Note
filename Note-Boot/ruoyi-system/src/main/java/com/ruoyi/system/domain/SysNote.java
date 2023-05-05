package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 笔记对象 sys_note
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
public class SysNote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 笔记ID */
    private Long noteId;

    /** 笔记标题 */
    @Excel(name = "笔记标题")
    private String noteTitle;

    /** 提醒日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "提醒日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reminderDate;

    /** 笔记内容 */
    @Excel(name = "笔记内容")
    private String noteContent;

    /** 笔记状态 */
    @Excel(name = "笔记状态")
    private String noteStatus;

    /** 笔记类型 */
    @Excel(name = "笔记类型")
    private String noteType;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setNoteId(Long noteId) 
    {
        this.noteId = noteId;
    }

    public Long getNoteId() 
    {
        return noteId;
    }
    public void setNoteTitle(String noteTitle) 
    {
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() 
    {
        return noteTitle;
    }
    public void setReminderDate(Date reminderDate) 
    {
        this.reminderDate = reminderDate;
    }

    public Date getReminderDate() 
    {
        return reminderDate;
    }
    public void setNoteContent(String noteContent) 
    {
        this.noteContent = noteContent;
    }

    public String getNoteContent() 
    {
        return noteContent;
    }
    public void setNoteStatus(String noteStatus) 
    {
        this.noteStatus = noteStatus;
    }

    public String getNoteStatus() 
    {
        return noteStatus;
    }
    public void setNoteType(String noteType) 
    {
        this.noteType = noteType;
    }

    public String getNoteType() 
    {
        return noteType;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noteId", getNoteId())
            .append("noteTitle", getNoteTitle())
            .append("reminderDate", getReminderDate())
            .append("noteContent", getNoteContent())
            .append("noteStatus", getNoteStatus())
            .append("noteType", getNoteType())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
