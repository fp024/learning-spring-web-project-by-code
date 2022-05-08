<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <title>Ajax 파일 업로드 테스트 페이지</title>
  <style>
    .uploadResult {
      width: 100%;
      background-color: gray;
    }

    .uploadResult ul {
      display: flex;
      flex-flow: row;
      justify-content: center;
      align-items: center;
    }

    .uploadResult ul li {
      list-style: none;
      padding: 10px;
      align-content: center;
      text-align: center;
    }

    .uploadResult ul li img {
      width: 100px
    }

    .uploadResult ul li span {
      color: white;
    }

    .bigPictureWrapper {
      position: absolute;
      display: none;
      justify-content: center;
      align-items: center;
      top: 0%;
      width: 100%;
      height: 100%;
      z-index: 100;
      background: rgba(255, 255, 255, 0.5);
    }

    .bigPicture {
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .bigPicture img {
      width: 600px;
    }
  </style>
</head>

<body>
<h1>Upload with Ajax</h1>

<div class="uploadDiv">
  <input type="file" name="uploadFile" multiple>
</div>

<div class="uploadResult">
  <ul>

  </ul>
</div>

<button id="uploadBtn">Upload</button>

<!-- 원본 이미지 보여주는 레이어 -->
<div class="bigPictureWrapper">
  <div class="bigPicture">

  </div>
</div>

<script>
  function showImage(fileCallPath) {
    const bigPictureWrapper = document.querySelector(".bigPictureWrapper")
    bigPictureWrapper.style.display = 'flex'

    const bigPicture = document.querySelector(".bigPicture")

    const img = document.createElement('img')
    img.setAttribute('src', 'display?fileName=' + encodeURI(fileCallPath))
    bigPicture.textContent = ''
    bigPicture.insertAdjacentElement('beforeend', img)
    bigPicture.animate([
      {transform: 'scale(0)'},
      {transform: 'scale(1)'}
    ], {
      duration: 1000,
      iterations: 1,
    })
  }

  document.addEventListener('DOMContentLoaded', () => {

    // 원본 이미지 보기 레이어 닫기
    document.querySelector(".bigPictureWrapper").addEventListener("click", function (e) {
      console.log(this)
      document.querySelector(".bigPicture").animate([
        {transform: 'scale(1)'},
        {transform: 'scale(0)'}
      ], {
        duration: 1000,
        iterations: 1
      })
      setTimeout(function () {
        document.querySelector(".bigPictureWrapper").style.display = 'none'
      }, 950)
    })

    const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
    const maxSize = 1024 * 1024 * 5 // web.xml에 단일 파일 최대 크기를 20MB로 정의했었는데, 5MB로 해보자.

    const uploadResult = document.querySelector('.uploadResult ul')

    function showUploadedFile(uploadResultArr) {
      let str = ""
      for (const obj of uploadResultArr) {
        if (!obj.image) {
          const fileCallPath = encodeURIComponent(
              obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName)
          str += "<li><a href='/download?fileName=" + fileCallPath
              + "'><img src='/resources/img/attach.png'>" + obj.fileName + "</a></li>";
        } else {
          const fileCallPath = encodeURIComponent(
              obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
          const originPath =
              obj.uploadPath.replace(new RegExp(/\\/g), "/") + "/" + obj.uuid + "_" + obj.fileName;
          console.log(originPath);
          str += "<li>"
              + "<a href=\"javascript:showImage(\'" + originPath + "\')\">"
              + "<img src='/display?fileName=" + fileCallPath + "'>"
              + "</a>"
              + "</li>";
        }
      }
      uploadResult.insertAdjacentHTML('beforeend', str)
    }

    function checkExtension(fileName, fileSize) {
      if (fileSize > maxSize) {
        alert("파일 사이즈 초과")
        return false
      }

      if (regex.test(fileName)) {
        alert("해당 종류의 파일은 업로드할 수 없습니다.")
        return false
      }
      return true
    }

    const cloneObj = document.querySelector('.uploadDiv').cloneNode(true)

    document.querySelector('button').addEventListener('click', () => {
      const formData = new FormData()
      const inputFile = document.querySelector('input[name="uploadFile"]')
      const files = inputFile.files // input이 1개가 명백하니, querySelector를 사용했고, 이것은 배열을 반환하지 않으므로, 배열참조를 제거하자!

      console.log(files)

      // 파일 데이터를 폼 데이터에 추가
      for (const file of files) {
        if (!checkExtension(file.name, file.size)) {
          return false
        }
        formData.append("uploadFile", file)
      }

      fetch("/uploadAjaxAction", {
        method: "POST",
        body: formData
      }).then(response => response.json())
      .then(result => {
        console.log(result)

        showUploadedFile(result)

        document.querySelector('.uploadDiv').replaceWith(cloneObj)
      })
    })
  })
</script>

</body>
</html>
