<%-- 
    Document   : editAuthor
    Created on : Oct 17, 2017, 6:55:18 PM
    Author     : kevinjerke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="edu.wctc.distjava.khj.bookwebapp.model.Author"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <title>Edit Author</title>
    </head>
    <body>
       <div class="container">
            <div class="row">
                <div class="col">
                    <h1>Edit Author </h1>

                    <form name="update" method ="POST" action = "authorController?action=update">

                        <input  type="hidden" name="id" value="${id}">            
                        Author Name:
                        <br>
                        <input class="form-control" type="text" name="name" value="">
                        <br>           
                        <input type="submit" class="btn btn-primary btn-lg active" name="submit" value="Submit">
                        <br><br>

                    </form>

                    </form>

                    <form name="home" method="POST" action="authorController?action=list" > 
                        <input class="btn btn-primary btn-lg active"   type="submit" value="Back to Authors List" />
                    </form>
                         <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
                    <script
                        src="https://code.jquery.com/jquery-3.2.1.min.js"
                        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
                        crossorigin="anonymous">
                    </script>
                    <!-- Bootstrap minified JavaScript -->
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

                </div>
            </div>
        </div>
    </body>
</html>
