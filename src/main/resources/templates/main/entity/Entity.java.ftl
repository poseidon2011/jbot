package ${packageName}.${projectName}.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "${tableName}")
public class ${tableName4FUH}Entity implements Serializable {

    private static final long serialVersionUID = 1L;

<#list fields as field>
    <#if field.fieldName=="id">@Id</#if>
    <#if field.fieldType=="Date">@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")</#if>
    @Column(name="${field.fieldName}")
    private ${stringUtil.sqlType2JavaType(field.fieldType)} ${field.fieldName};
</#list>

}
