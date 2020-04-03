<xsl:template match="screen">
    <HTML><BODY bgcolor="white">
        <xsl:apply-templates/>
    </BODY></HTML>
</xsl:template>
<xsl:template match="title">
<h1><xsl:apply-templates/></h1>
</xsl:template><xsl:template match="field">
<P><B><xsl:value-of select = "@label"/>: </B><xsl:apply-templates/></P>
</xsl:template>
<xsl:template match="table">
<table><xsl:apply-templates/></table>
</xsl:template>
<xsl:template match="table/row">
<xsl:variable name="bgcolor">
    <xsl:choose>
        <xsl:when test="(position() mod 2) = 1">linen</xsl:when>
        <xsl:otherwise>white</xsl:otherwise>
    </xsl:choose>
    </xsl:variable>
        <tr bgcolor="{$bgcolor}"><xsl:apply-templates/></tr>
    </xsl:template>
<xsl:template match="table/row/cell">
    <td><xsl:apply-templates/></td>
</xsl:template>