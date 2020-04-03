<xsl:template match="album">
    <screen><xsl:apply-templates/></screen>
</xsl:template>
<xsl:template match="album/title">
    <title><xsl:apply-templates/></title>
</xsl:template>
<xsl:template match="artist">
    <field label="Artist"><xsl:apply-templates/></field>
</xsl:template>
<xsl:template match="trackList">
    <table><xsl:apply-templates/></table>
</xsl:template>
<xsl:template match="track">
    <row><xsl:apply-templates/></row>
</xsl:template>
<xsl:template match="track/title">
    <cell><xsl:apply-templates/></cell>
</xsl:template>
<xsl:template match="track/time">
    <cell><xsl:apply-templates/></cell>
</xsl:template>