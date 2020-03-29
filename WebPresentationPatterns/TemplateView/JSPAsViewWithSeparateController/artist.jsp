<jsp:useBean id="helper" type="actionController.ArtistHelper" scope="request"/>


<B> <%=helper.getName()%></B>

<B><jsp:getProperty name="helper" property="name"/></B>

<UL>
<%
    for (Iterator it = helper.getAlbums().iterator(); it.hasNext();) {
        Album album = (Album) it.next();%>
    <LI><%=album.getTitle()%></LI>
<% } %>
</UL>

<UL>
    <tag:forEach host = "helper" collection = "albums" id = "each">
        <LI><jsp:getProperty name="each" property="title"/></LI>
    </tag:forEach>
</UL>