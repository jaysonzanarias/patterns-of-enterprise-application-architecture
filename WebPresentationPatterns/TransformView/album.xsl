<xsl:template match="album">
<HTML><BODY bgcolor="white">
    <xsl:apply-templates/>
</BODY></HTML>
</xsl:template>
<xsl:template match="album/title">
<h1><xsl:apply-templates/></h1>
</xsl:template>
<xsl:template match="artist">
<P><B>Artist: </B><xsl:apply-templates/></P>
</xsl:template>


<xsl:template match="trackList">
<table><xsl:apply-templates/></table>
</xsl:template>
<xsl:template match="track">
<xsl:variable name="bgcolor">
    <xsl:choose>
        <xsl:when test="(position() mod 2) = 1">linen</xsl:when>
        <xsl:otherwise>white</xsl:otherwise>
    </xsl:choose>
</xsl:variable>
<tr bgcolor="{$bgcolor}"><xsl:apply-templates/></tr>
</xsl:template>
<xsl:template match="track/title">
<td><xsl:apply-templates/></td>
</xsl:template>
<xsl:template match="track/time">
<td><xsl:apply-templates/></td>
</xsl:template>