/**
 * @name webUploadUtil
 * @version 1.2
 * @desc 该工具依赖jQuery及webuploader插件
 *
 * 使用范例：
 * jsp页面在需要使用上传插件的区域加入
 * <div id='demo1' style="margin-top: 10px;"></div>
 * 代码,id必须指定且唯一
 *
 * js代码
 *
 * $(function(){
 * 		WebUploadUtil.init('demo1', {});
 * });
 *
 * 第一个参数为id，第二个参数为自定义对象，对象参数见 defaultParam
 *
 * 上传之后的文件id通过
 * $('input[id^="sysFile_"]')的方式获取
 * $('input[id^="sysFile_demo1_"]')的方式获取
 *
 * 上传前
 * uploadBeforeSend(obj, data)
 * data用于像后端传入参数
 *
 * 上传成功后
 * uploadComplete(file)
 * 上传之后需要做的操作
 *
 * 一般需要对这两个方法重写，以参数的形式传入
 *
 * 如果需要对上传对象进行其他重写
 * 可以使用
 * WebUploadUtil.space.demo1 获取该上传对象
 *
 *
 **/
(function (factory) {
    if (typeof define === 'function' && define.Uploader.swfamd) {
        define(['webuploader'], factory);
    } else {
        factory(WebUploader);
    }
})(function (WebUploader) {
    window.WebUploadUtil = {
        space: {},
        //初始参数
        defaultParam: {
            fileInputId: '',
            parentFileInputId: '',
            // swf文件路径
            swf: 'js/Uploader.swf',
            // 文件接收服务端。
            server: '/upload/autoSaveFile.do',
            // 选择文件的按钮
            // pick: '#ceshi',
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            //文件类型
            accept: {
                extensions: 'bpmn',
            },
            // 传到后台的数据
            formData: {},
            //数量
            fileNumLimit: 1,
            //全部文件大小上限 单位b
            fileSizeLimit: 2000000000,
            //单文件大小上限 单位b
            fileSingleSizeLimit: 200000000,
            //去重
            duplicate: false,
            //自动上传
            auto: true,
            // 按钮模式
            fileQueuedFlag: null,
            uploadBeforeSend: function (obj, data) {
            },
            uploadComplete: function (file) {
                //toastr.success("上传成功！");
            },
            //上传类型:导入上传(import)，普通文件上传等lsy20180613
            uploadType: "",
            extImg: {
                accdb: "accdb.png", avi: "avi.png", bmp: "bmp.png", css: "css.png", docx: "docx.png", doc: "word.png",
                eml: "eml.png", eps: "eps.png", fla: "fla.png", gif: "gif.png", html: "html.png", jpg: "jpg.png",
                ind: "ind.png", ini: "ini.png", jpeg: "jpeg.png", jsf: "jsf.png", midi: "midi.png",
                mov: "mov.png", mp3: "mp3.png", mpeg: "mpeg.png", pdf: "pdf.png", png: "png.png", ppt: "pptx.png",
                pptx: "pptx.png", proj: "proj.png", psd: "psd.png", pst: "pst.png", pub: "pub.png",
                rar: "rar.png", readme: "readme.png", settings: "settings.png", tiff: "tiff.png",
                txt: "txt.png", url: "url.png", vsd: "vsd.png", wav: "wav.png", wma: "wma.png",
                wmv: "wmv.png", xlsx: "xlsx.png", xls: "xls.png", zip: "zip.png", other: "files.png"
            }

        },
        //参数
        settings: {},
        //初始化
        init: function (id, param, formsId) {
            var _this = this;
            var _uploadAreaDiv = $("#" + id);

            /*if(WebUploadUtil.brower() == 'Chrome'){
                var uploadify_input = $('<input id="uploadify_input_' + id + '" type="file"  />');
                _uploadAreaDiv.append(uploadify_input);
                WebUploadUtil.fuploadify("uploadify_input_" + id,"上传文件");
            }else{*/
            $.extend(this.settings, this.defaultParam, param);
            // 把要拼接的参数拼接到url后
            this.settings.pick = '#' + id + '_initContanier';
            this.settings.dnd = '#' + id + '_initContanier';
            var queueFile = $('<div style="margin-top: 10px;height:auto;max-height:236px;overflow-y:auto;" id="' + id + '_fileQueueList"></div>');
            var buttons = $('<a class="btn btn-info">开始上传</a>' +
                '<a class="btn btn-warning">停止上传</a>' +
                '<a class="btn btn-default">取消上传</a>');
            var fileContanier = $('<div id="' + id + '_initContanier" class="file_upload" style="border:1px dashed lightgray;height:60px;line-height:60px;margin-top:10px;text-align:center;color:lightgray;">点击选择文件，或者把文件拖拽到这里</div>');
            var fileButton = $('<div id="' + id + '_initContanier" class="file_upload_btn"><i class="fa fa-cloud-upload"></i>上传文件</div>');
            if (this.settings.chunked) {
                _uploadAreaDiv.append(buttons);
            }
            if (this.settings.fileQueuedFlag == null) {
                _uploadAreaDiv.append(fileContanier).append(queueFile);
            } else if (this.settings.fileQueuedFlag) {
                _uploadAreaDiv.before(fileButton).append(queueFile);
            }
            var _uploader = WebUploader.create(this.settings);
            //上传之前获取参数
            _uploader.on('uploadBeforeSend', this.settings.uploadBeforeSend);
            //
            var flag = true;
            _uploader.on('uploadSuccess', this.settings.uploadSuccess || function (file, response) {
                if (response.success == false || (response.code != "1" && null != response.msg)) {
                    flag = false;
                    if (response.success == false) {
                        toastr.warning(response.message);
                    } else {
                        toastr.warning(response.msg);
                    }
                    return false;
                } else {
                    flag = true;
                }

                $('#download_' + id + '_' + file.id).children("a").attr('href', path + '/admin/sysFile/downloadFile.do?id=' + response.obj);
                //加入系统文件id，注意此id与file的id是不一样的，file的id是控件自动生成的
                $('#sysFile_' + id + '_' + file.id).val(response.obj);
//				$('#fileIds').val(response.obj);
                $('#download_' + id + '_' + file.id).parents('form').find('#fileIds').val(response.obj);
                //添加对应的id
                // 自定义表单验证用的
                var testId = id.replace("_div", "");
                $("[name='" + testId + "_fileIds'").val($("[name='" + testId + "_fileIds'").val() + file.id);
                var idStr = $('#download_' + id + '_' + file.id).parents('form').find('#fileIds').data("ids");
                if (_this.settings.fileInputId != '') {
                    $("#" + _this.settings.fileInputId).val($("#" + _this.settings.fileInputId).val() + "," + response.obj);
                } else {
                    $("#" + idStr).val($("#" + idStr).val() + "," + response.obj);
                }
                $("[name='" + testId + "_fileIds'").blur();
            });
            //全部上传完成
            _uploader.on('uploadComplete', function (file) {
                if (!flag) {
                    setTimeout(function () {
                        $('#progressbar_' + id + '_' + file.id).parent().remove();
                        $('#download_' + id + '_' + file.id).children(".data").text("上传失败!");
                        if (_this.settings.uploadType == "import") {//如果是导入的上传类型lsy 20180613
                            WebUploadUtil.removeFile(id, file.id);
                        }

                    }, 1000);
                    return false;
                }
                var fileMess = $("#" + id + "_" + file.id);
                var oper = $('<div style="float:right;margin-right:5px;"><i class="glyphicon glyphicon-trash removeFile" title="删除" onclick="WebUploadUtil.removeFile(\'' + id + '\',\'' + file.id + '\')"></i>&nbsp;&nbsp;<i class="fa fa-check-circle" style="cursor:pointer;color:#5cb85c;font-size:24px;" title="上传成功"></i></div>');
                setTimeout(function () {
                    //console.log("setTimeout 执行");
                    $('#progressbar_' + id + '_' + file.id).parent().remove();
                    $('#download_' + id + '_' + file.id).children(".data").text("恭喜你,上传成功!");
                    $('#' + id + '_' + file.id).append(oper);
                    if (_this.settings.uploadType == "" || _this.settings.uploadType == null) {//如果不是导入的上传类型lsy20180613
                        $('#download_' + id + '_' + file.id).children("a").append("&nbsp;&nbsp;<i class='fa fa-download'></i>");
                    }
                }, 1000);
                /*fileMess.append(flieAcMes);*/

                if (_this.settings.uploadType == 'import' && null != formsId && formsId != '') {
                    toastr.success("导入成功！");
                    $("#" + formsId).closest('.modal').modal('hide');
                } else {
                    toastr.success("上传成功！");
                }

                //添加完成后执行事件 update by liutie 2018/04/25
                if (_this.settings.uploadComplete) {
                    _this.settings.uploadComplete(file);
                }
            });
            //进度条
            _uploader.on('uploadProgress', function (file, percentage) {
                $('#progressbar_' + id + '_' + file.id).css('width', percentage * 100 + '%');
                $('#download_' + id + '_' + file.id).children(".data").text(percentage * 100 + "%");
            });
            // 添加文件后
            _uploader.on('fileQueued', function (file) {
                var queueFile = $('#' + id + '_fileQueueList');
                var extImg = $('<div style="float:left;width:50px;margin-right:5px;height:60px;height:60px;"><img src="' + path + '/admin/pages/workflow/deploy/images/' + (_this.settings.extImg[file.ext] || _this.settings.extImg['other']) + '" style="height:40px;width:40px;"></div>');
                var fileList = $('<div class="file_list-item" id="' + id + '_' + file.id + '"></div>');
                var fileInput = $('<input type="hidden" id="sysFile_' + id + '_' + file.id + '" value=""/>');
                var fileProgress = '<div class="progress progress-striped active file_progess">' +
                    '<div class="progress-bar progress-bar-info" id="progressbar_' + id + "_" + file.id + '" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:0">' +
                    '</div></div>';
                var fileDetail = $('<span id="download_' + id + '_' + file.id + '" style="float:left;height:60px;line-height:60px;"><span class="file_detail" title="' + file.name + "(" + WebUploadUtil.getFileSize(file.size) + ')">' + file.name + "(" + WebUploadUtil.getFileSize(file.size) + ')</span><span class="data" style="font-size:12px;"></span><a target="_blank" href=""></a></span>');
                fileList.append(extImg).append(fileInput).append(fileDetail).append(fileProgress);
                queueFile.append(fileList);
            });
            _uploader.on('ready', function () {
                // 修改时候出发ready, 处理回写
                // window.uploader = uploader;
                // alert(WebUploader.Base.version);
//            alert(WebUploader.File.name);

                // alert(id);
//                 if(fileid){
//                     //获取数据
                    $.post('/fileList',{},function(json){
                        var jsonLen=json.rows.length;
                        if(jsonLen!=0){
                            var queueFile = $('#' + id + '_fileQueueList');
                            //显示在页面上
                            $.each(json.rows,function(i,file){
                                var extImg = $('<div style="float:left;width:50px;margin-right:5px;height:60px;height:60px;"></div>');
                                var fileList = $('<div class="file_list-item" id="' + id + '_' + file.id + '"></div>');
                                var fileInput = $('<input type="hidden" id="sysFile_' + id + '_' + file.id + '" value=""/>');
                                var fileProgress = '<div class="progress progress-striped active file_progess">' +
                                    '<div class="progress-bar progress-bar-info" id="progressbar_' + id + "_" + file.id + '" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:0">' +
                                    '</div></div>';
                                var fileDetail = $('<span id="download_' + id + '_' + file.id + '" style="float:left;height:60px;line-height:60px;"><span class="file_detail" title="' + file.fileName + "(" + WebUploadUtil.getFileSize(file.fileSize) + ')">' + file.fileName + "(" + WebUploadUtil.getFileSize(file.fileSize) + ')</span><span class="data" style="font-size:12px;"></span><a target="_blank" href=""></a></span>');
                                fileList.append(extImg).append(fileInput).append(fileDetail).append(fileProgress);
                                queueFile.append(fileList);
                            });

                            WebUploader.Base.idSuffix=jsonLen;

                            // setState( 'ready' );
                        }
                    },'json');
            });
            _uploader.on('uploadError', function (file) {
                _uploader.retry();
            });
            // 文件错误
            _uploader.on('error', function (type) {
                if (type == 'Q_EXCEED_NUM_LIMIT') {
                    //tip("上传的文件数量已经超出系统限制的"+ _uploader.option('fileNumLimit')+"个文件！");
                    toastr.warning("上传的文件数量已经超出系统限制的" + _uploader.option('fileNumLimit') + "个文件！");
                } else if (type == 'Q_EXCEED_SIZE_LIMIT') {
                    //tip("文件大小超出系统限制的"+parseInt( _uploader.option('fileSizeLimit'))/1000000+"M 大小！");
                    toastr.warning("文件大小超出系统限制的" + parseInt(_uploader.option('fileSizeLimit')) / 1000000 + "M 大小！");
                } else if (type == 'Q_TYPE_DENIED') {
                    //tip("文件类型不正确！");
                    if (_uploader.option('fileTypeExts')) {
                        toastr.warning("只允许上传后缀为" + _uploader.option('fileTypeExts') + "的文件！");
                    } else {
                        toastr.warning("文件内容为空或者文件类型不正确！");
                    }
                } else if (type == 'F_EXCEED_SIZE') {
                    toastr.warning("文件大小超出系统限制的" + parseInt(_uploader.option('fileSingleSizeLimit')) / 1000000 + "M 大小！");
                }
            });
            //}
            //绑定到名空间
            this.space[id] = _uploader;
        },
        removeFile: function (uploader, id) {
            var _this = this;
            var str = 'WebUploadUtil.space["' + uploader + '"].removeFile(\'' + id + '\', true)';
            eval(str);
            //uploader.removeFile(id, true);
            //删除文件

            if (_this.settings.uploadType == "import") {//如果是导入的上传类型 lsy20180613
                $('#' + uploader + '_' + id).remove();
            } else {
                var file_id = $('#sysFile_' + uploader + '_' + id).val();
                if (file_id) {
                    $.get(path + '/admin/sysFile/deleteFile.do?ids=' + file_id, function (data) {
                        $('#' + uploader + '_' + id).remove();
                    });
                }
                //删除对应的id
                var idStr = $('#sysFile_' + uploader + '_' + id).parents('form').find('#fileIds').data("ids");

                if (_this.settings.fileInputId != '') {
                    $("#" + _this.settings.fileInputId).val($("#" + _this.settings.fileInputId).val().split("," + file_id).join(""));
                } else {
                    $("#" + idStr).val($("#" + idStr).val().split("," + file_id).join(""));
                }
                // 自定义表单验证用的
                var testId = uploader.replace("_div", "");
                if ($("[name='" + testId + "_fileIds'").val() != undefined) {
                    $("[name='" + testId + "_fileIds'").val($("[name='" + testId + "_fileIds'").val().replace(id, ""));
                    $("[name='" + testId + "_fileIds'").blur();
                }
            }

        },
        //通过id删除
        removeFileById: function (id) {
            if (!id) {
                return false;
            }
            $jzee.confirm({message: "确认删除？"}).on(function (e) {
                if (!e) {
                    return;
                }
                //删除文件
                $.get(path + '/admin/sysFile/deleteFile.do?ids=' + id, function (data) {
                    $('#' + id).remove();
                });

                //删除对应的id
                var $fileIdsInput = $('#file_fileIds');
                ;
                if ($fileIdsInput.val() && fileIdsInput.indexOf(id) >= 0) {
                    var newFileIds = $fileIdsInput.val().replace(id + ',').replace(id);
                }
            });
        },
        //获取该上传文件数
        queryFileSize: function (id) {
            return this.space[id].getFiles().length - this.space[id].getStats().cancelNum;
        },
        queryFileIds: function (id) {
            var pickReg = 'input[id^="sysFile_"]:hidden';
            if (id) {
                pickReg = 'input[id^="sysFile_' + id + '"]:hidden';
            }
            var $sysFiles_ = $(pickReg);
            if ($sysFiles_ && $sysFiles_.length > 0) {
                var files = '';
                $sysFiles_.each(function () {
                    files += ',' + $(this).val();
                });
                return files.substring(1);
            }
            return '';
        },
        //计算文件大小
        getFileSize: function (fileByte) {
            var fileSizeByte = fileByte;
            var fileSizeMsg = "";
            if (fileSizeByte < 1048576) fileSizeMsg = (fileSizeByte / 1024).toFixed(2) + "KB";
            else if (fileSizeByte == 1048576) fileSizeMsg = "1MB";
            else if (fileSizeByte > 1048576 && fileSizeByte < 1073741824) fileSizeMsg = (fileSizeByte / (1024 * 1024)).toFixed(2) + "MB";
            else if (fileSizeByte > 1048576 && fileSizeByte == 1073741824) fileSizeMsg = "1GB";
            else if (fileSizeByte > 1073741824 && fileSizeByte < 1099511627776) fileSizeMsg = (fileSizeByte / (1024 * 1024 * 1024)).toFixed(2) + "GB";
            else fileSizeMsg = "文件超过1TB";
            return fileSizeMsg;
            return fileByte;
        },
        // 格式化时间
        formatDate: function (date, formatStr) {
            var str = formatStr;
            var Week = ['日', '一', '二', '三', '四', '五', '六'];
            str = str.replace(/yyyy|YYYY/, date.getFullYear());
            str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (this.getYear() % 100));
            var month = date.getMonth() + 1;
            str = str.replace(/MM/, month > 9 ? month.toString() : '0' + month);
            str = str.replace(/M/g, month);

            str = str.replace(/w|W/g, Week[date.getDay()]);

            str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
            str = str.replace(/d|D/g, date.getDate());

            str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
            str = str.replace(/h|H/g, date.getHours());
            str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
            str = str.replace(/m/g, date.getMinutes());

            str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
            str = str.replace(/s|S/g, date.getSeconds());
            return str;
        },
        fuploadify: function (control_field, btnName) {
            $("#" + control_field).uploadify({
                method: 'post',
                uploader: path + '/admin/sysFile/autoSaveFile.do',
                swf: path + '/admin/assets/global/plugins/uploadify/uploadify.swf',
                buttonText: btnName,
                height: 30,
                width: 90,
                fileTypeExts: '*.avi;*.mp3;*.mp4;*.bmp;*.ico;*.gif;*.jpeg;*.jpg;*.png;*.psd; *.rar;*.zip;*.swf;*.log;*.pdf;*.doc;*.docx;*.ppt;*.pptx;*.txt; *.xls; *.xlsx;',
                removeCompleted: false,
                onSelect: function (file) {
                    $("#" + file.id).prepend('<div style="float:left;width:50px;margin-right:2px;"><img src="' + path + '/admin/pages/workflow/deploy/images/' + WebUploadUtil.defaultParam.extImg[file.type.replace('.', '')] + '" style="width:40px;height:40px;" /></div>');

                    $(".uploadify-queue-item").find('.cancel').find('a').html('<i class="fa fa-trash-o "></i>');
                    $(".uploadify-queue-item").find('.cancel').find('a').attr('title', '删除');
                    $(".uploadify-queue-item").hover(function () {
                        $(this).find('.cancel').find('a').show();
                    }, function () {
                        $(this).find('.cancel').find('a').hide();
                    });
                },
                onUploadSuccess: function (file) {
                    $("#" + file.id).find('.uploadify-progress').remove();
                    $("#" + file.id).find('.data').html(' 恭喜您，上传成功！');
                    $("#" + file.id).prepend('<a class="succeed" title="成功"><i class="fa fa-check-circle"></i></a>');
                    Loading(false);

                },
                onUploadError: function (file, errorCode, errorMsg) {
                    console.log(errorMsg);
                    $("#" + file.id).removeClass('uploadify-error');
                    $("#" + file.id).find('.uploadify-progress').remove();
                    $("#" + file.id).find('.data').html(' 很抱歉，上传失败！');
                    $("#" + file.id).prepend('<span class="error" title="失败"><i class="fa fa-exclamation-circle"></i></span>');
                },
                onUploadStart: function () {
                    $('#' + control_field + '-queue').show();
                },
                onCancel: function (file) {
                }
            });
            $("#" + control_field + "-button").prepend('<i style="opacity: 0.6;" class="fa fa-cloud-upload"></i>&nbsp;');
            $('#' + control_field + '-queue').hide();
        },
        // 判断浏览器
        brower: function () {
            return (function (brower) {
                var isOpera = brower.indexOf("Opera") > -1;
                if (isOpera) {
                    return "Opera"
                }
                ; //判断是否Opera浏览器
                if (brower.indexOf("Firefox") > -1) {
                    return "FF";
                } //判断是否Firefox浏览器
                if (brower.indexOf("Chrome") > -1) {
                    return "Chrome";
                }
                if (brower.indexOf("Safari") > -1) {
                    return "Safari";
                } //判断是否Safari浏览器
                if (brower.indexOf("compatible") > -1 && brower.indexOf("MSIE") > -1 && !isOpera) {
                    return "IE";
                }
                ; //判断是否IE浏览器
            })(navigator.userAgent);
        },
        // 判断用户在是否安装了flash和flash版本
        flashChecker: function () {
            var hasFlash = 0; //是否安装了flash
            var flashVersion = 0; //flash版本
            var isIE = 0;      //是否IE浏览器
            if (WebUploadUtil.brower() == 'IE') {
                isIE = 1;
            }
            if (isIE) {
                var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
                if (swf) {
                    hasFlash = 1;
                    VSwf = swf.GetVariable("$version");
                    flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
                }
            } else {
                if (navigator.plugins && navigator.plugins.length > 0) {
                    var swf = navigator.plugins["Shockwave Flash"];
                    if (swf) {
                        hasFlash = 1;
                        var words = swf.description.split(" ");
                        for (var i = 0; i < words.length; ++i) {
                            if (isNaN(parseInt(words[i]))) continue;
                            flashVersion = parseInt(words[i]);
                        }
                    }
                }
            }
            return {f: hasFlash, v: flashVersion};
        }
    }
});