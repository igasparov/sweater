<#include "security.ftlh">
<#import "pager.ftlh" as p>

<@p.pager url page />
<div class="mt-3"><h4>Messages</h4></div>

<div id="message-list" class="row row-cols-1 row-cols-md-3 g-4">
    <#list page.content as message>
        <div class="col">
            <div class="card" data-id="${message.id}">
                <#if message.filename??><img src="/img/${message.filename}"></#if>
                <div class="card-body">
                    <h5 class="card-title">#${message.tag}</h5>
                    <p class="card-text">${message.text}</p>
                    <div class="card-footer container">
                        <div class="row">
                            <a class="col align-self-center"
                               href="/messages/${message.author.id}">${message.authorName}</a>
                            <a class="col align-self-center" href="/messages/${message.id}/like">
                                <#if message.meLiked>
                                    <i class="fa-sharp fa-solid fa-heart fa-fade"></i>
                                <#else>
                                    <i class="fa-sharp fa-regular fa-heart fa-beat"></i>
                                </#if>
                                ${message.likes}
                            </a>
                            <#if message.author.id == currentUserId>
                                <a class="col btn btn-primary" href="/messages/${message.author.id}?message=${message.id}">Edit</a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
<@p.pager url page />