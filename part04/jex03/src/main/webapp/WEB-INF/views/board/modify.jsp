<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time"%>
<!DOCTYPE html>
<html lang="ko">

<%@include file="../includes/header.jsp"%>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- topbar도 별도 파일에 분리하자! -->
        <%@include file="../includes/topbar.jsp"%>

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Board Modify Page</h1>


          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Board Modify Page</h6>
            </div>
            <div class="card-body">
              <form role="form" action="/board/modify" method="post">
                <input type="hidden" name="pageNum" value="<c:out value="${criteria.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${criteria.amount}"/>">
                <input type="hidden" name="searchCodes" value="<c:out value="${criteria.searchCodesWithJoined}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${criteria.keyword}"/>">
              
                <div class="form-group">
                  <label>Bno</label> <input class="form-control" name="bno" value="<c:out value='${board.bno}'/>" readonly="readonly">
                </div>
                <div class="form-group">
                  <label>Title</label> <input class="form-control" name="title" value="<c:out value='${board.title}'/>">
                </div>
                <div class="form-group">
                  <label>Text area</label>
                  <textarea class="form-control" rows="5" name="content"><c:out value='${board.content}' /></textarea>
                </div>
                <div class="form-group">
                  <label>Writer</label> <input class="form-control" name="writer" readonly="readonly" value="<c:out value='${board.writer}'/>">
                </div>
                <button type="submit" data-oper="modify" class="btn btn-warning">Modify</button>
                <button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
                <button type="submit" data-oper="list" class="btn btn-secondary">List</button>
              </form>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->


      <!-- Footer -->
      <%@include file="../includes/footer.jsp"%>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->
  <%@include file="../includes/dialogAndScript.jsp"%>

  <script type="text/javascript">
      $(document).ready(function() {
        var $formObj = $("form");

        $formObj.find("button").on("click", function(e) {
          e.preventDefault();

          var operation = $(this).data("oper"); // data-oper 속성을 이렇게 읽는구나?

          console.log(operation);

          if (operation === "remove") {
            $formObj.attr("action", "/board/remove");

          } else if (operation === "list") {
            $formObj.attr("action", "/board/list").attr("method", "get");
            var pageNumTag = $("input[name='pageNum']").clone();
            var amountTag = $("input[name='amount']").clone();
            var searchCodes = $("input[name='searchCodes']").clone();
            var keyword = $("input[name='keyword']").clone();

            $formObj.empty();
            $formObj.append(pageNumTag);
            $formObj.append(amountTag);
            $formObj.append(searchCodes);
            $formObj.append(keyword);
          }
          $formObj.submit();
        });
      });
    </script>

</body>

</html>