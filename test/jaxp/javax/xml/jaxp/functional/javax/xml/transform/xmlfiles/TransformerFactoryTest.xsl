<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
		<html> 
		<xsl:apply-templates/>
		</html>
</xsl:template>
<xsl:template match="Review">
		<body>
	        <xsl:apply-templates select="//About"/>
		</body>
</xsl:template>
<xsl:template match="About">
	<xsl:for-each select="Text">
<Br>
	<li>
		<font size ="4" color ="Blue">
<xsl:value-of select="."/>
		</font>
		</li>
	</Br>
		<xsl:apply-templates/>
		</xsl:for-each>
</xsl:template>
</xsl:stylesheet>
