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
          <h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>


          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
            </div>
            <div id="board-read-body" class="card-body">
              <div class="form-group">
                <label>Bno</label> <input class="form-control" name="bno" value="<c:out value='${board.bno}'/>" readonly="readonly">
              </div>
              <div class="form-group">
                <label>Title</label> <input class="form-control" name="title" value="<c:out value='${board.title}'/>" readonly="readonly">
              </div>
              <div class="form-group">
                <label>Text area</label>
                <textarea class="form-control" rows="5" name="content" readonly="readonly"><c:out value='${board.content}' /></textarea>
              </div>
              <div class="form-group">
                <label>Writer</label> <input class="form-control" name="writer" readonly="readonly" value="<c:out value='${board.writer}'/>">
              </div>
              <div class="form-group">
                <label>Register Date</label> <input class="form-control" name="regDate" readonly="readonly"
                  value="<javatime:format value="${board.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />">
              </div>
              <div class="form-group">
                <label>Update Date</label> <input class="form-control" name="regDate" readonly="readonly"
                  value="<javatime:format value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
              </div>
              <button data-oper="modify" class="btn btn-primary">Modify</button>
              <button data-oper="list" class="btn btn-secondary">List</button>

              <form id="operForm" action="/board/modify" method="get">
                <input type="hidden" id="bno" name="bno" value="<c:out value="${board.bno}"/>">
                <input type="hidden" name="pageNum" value="<c:out value="${criteria.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${criteria.amount}"/>">
                <input type="hidden" name="searchCodes" value="<c:out value="${criteria.searchCodesWithJoined}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${criteria.keyword}"/>">
              </form>
            </div>
          </div>

          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <div>
                <span class="font-weight-bold text-primary"><i class="fa fa-comments fa-fw"></i>Reply</span><button id="addReplyBtn" class="btn btn-primary btn-sm float-md-right">New Reply</button>
              </div>
            </div>
            <div class="card-body">
              <ul id="replyUL" class="list-group list-group-flush">
                <!-- start reply -->
                <li class="list-group-item left clearfix" data-rno="12"></li>
                <!-- end reply -->
              </ul>
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

  <!-- 댓글 등록 Modal-->
  <div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="replyModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="replyModalLabel">REPLY MODAL</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>Reply</label>
            <input class="form-control" name="reply" value="New Reply!!!!!">
          </div>
          <div class="form-group">
            <label>Replyer</label>
            <input class="form-control" name="replyer" value="replyer">
          </div>
          <div class="form-group">
            <label>Reply Date</label>
            <input class="form-control" name="replyDate" value="">
          </div>
        </div>
        <div class="modal-footer">
          <button id="modalModBtn" class="btn btn-warning" type="button" data-dismiss="modal">Modify</button>
          <button id="modalRemoveBtn" class="btn btn-danger" type="button" data-dismiss="modal">Remove</button>
          <button id="modalRegisterBtn" class="btn btn-primary" type="button" data-dismiss="modal">Register</button>
          <button id="modalCloseBtn" class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript" src="/resources/js/reply.js"></script>
  <script type="text/javascript">
    var bnoValue = '<c:out value="${board.bno}"/>';
    var replyUL = $('#replyUL');

    showList(1);

    function showList(page) {
      replyService.getList({bno: bnoValue, page: 1}, function (list) {
        var str = "";
        if (list == null || list.length == 0) {
          replyUL.html("");
          return;
        }

        for (var i = 0, len = list.length || 0; i < len; i++) {
          str += '<li class="list-group-item left clearfix" data-rno=' + list[i].rno + '>';
          str += '<div>';
          str += '<div class="header">';
          str += '<strong class="font-weight-bold text-primary">' + list[i].replyer + '</strong>';
          str += '<small class="float-right text-muted">' + replyService.displayTime(list[i].replyDate) + '</small>';
          str += '</div>'
          str += '<p>' + list[i].reply + '</p>'
          str += '</div>'
          str += '</li>'
        }
        replyUL.html(str);
      });
    }

    var replyModal = $("#replyModal");
    var modalInputReply = replyModal.find("input[name='reply']");
    var modalInputReplyer = replyModal.find("input[name='replyer']");
    var modalInputReplyDate = replyModal.find("input[name='replyDate']");

    var modalModBtn = $("#modalModBtn");
    var modalRemoveBtn = $("#modalRemoveBtn");
    var modalRegisterBtn = $("#modalRegisterBtn");

    $("#addReplyBtn").on("click", function (e) {
      replyModal.find("input").val("");
      modalInputReplyDate.closest("div").hide();
      replyModal.find("button[id != 'modalCloseBtn']").hide();

      modalRegisterBtn.show();

      replyModal.modal("show");
    });

    modalRegisterBtn.on("click", function (e) {
      var reply = {
        reply: modalInputReply.val(),
        replyer: modalInputReplyer.val(),
        bno: bnoValue
      }

      replyService.add(reply, function (result) {
        alert(result);

        replyModal.find("input").val("");
        replyModal.modal("hide");

        showList(1);
      });
    })

    replyUL.on("click", "li", function (e) {
      var rno = $(this).data("rno");
      replyService.get(rno, function (reply) {
        modalInputReply.val(reply.reply);
        modalInputReplyer.val(reply.replyer);
        modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
        replyModal.data("rno", reply.rno);

        replyModal.find("button[id != 'modalCloseBtn']").hide();

        modalModBtn.show();
        modalRemoveBtn.show();

        replyModal.modal("show");
      });
    });

    modalModBtn.on("click", function (e) {
      var reply = {rno: replyModal.data("rno"), reply: modalInputReply.val()};
      replyService.update(reply, function (result) {
        alert(result);
        replyModal.modal("hide");
        showList(1);
      });
    });

    modalRemoveBtn.on("click", function (e) {
      var rno = replyModal.data("rno");

      replyService.remove(rno, function (result) {
        alert(result);
        replyModal.modal("hide");
        showList(1);
      });
    });
  </script>

  <script type="text/javascript">
    $(document).ready(function () {
      var $operForm = $("#operForm");

      $("#board-read-body button[data-oper='modify']").on("click", function (e) {
        $operForm.attr("action", "/board/modify").submit();
      });

      // 빈폼으로 Submit을 해버리면 결과 URL 끝에 ?가 붙는다.
      $("#board-read-body button[data-oper='list']").on("click", function (e) {
        $operForm.find("#bno").remove();
        $operForm.attr("action", "/board/list").submit();
        $operForm.submit();
      });
    });
  </script>
</body>

</html>