<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">Up</a>


<!-- 게시물 등록 완료 Modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal Title</h5>
        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div class="modal-body">처리가 완료되었습니다.</div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
        <a class="btn btn-primary" href="login.html">Logout</a>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="/resources/vendor/jquery/jquery.min.js"></script>
<script src="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="/resources/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<!-- // 페이징 지원 플러그인은 제거한다.
<script src="/resources/vendor/datatables/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>
-->

<script type="text/javascript">
  //$(document).ready(function() {
  //  $('#dataTable').DataTable();
  //});

  $(document).ready(function() {
    var result = '<c:out value="${result}" />';

    checkModal(result);

    // 뒤로가기시 모달창이 불필요하게 뜨는 것을 방지하기위해 history 조작
    history.replaceState({}, null, null);

    function checkModal(result) {
      if (result === '' || history.state) {
        return;
      }

      if (parseInt(result) > 0) {
        $("#myModal .modal-body").html("게시글 " + parseInt(result) + "번이 등록되었습니다.");
      }

      $("#myModal").modal("show");
    }

    $("#regBtn").on("click", function() {
      self.location = "/board/register";
    });

    var $actionForm = $("#actionForm");

    $(".page-item a").on("click", function(e) {
      e.preventDefault();

      console.log('click');

      $actionForm.find("input[name='pageNum']").val($(this).attr("href"));
      $actionForm.submit();
    });

    $(".move").on("click", function(e) {
      e.preventDefault();

      console.log('click - move');

      $actionForm.find("input[name='bno']").remove();
      $actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");
      $actionForm.attr("action", "/board/get");
      $actionForm.submit();
    });

  });
</script>

